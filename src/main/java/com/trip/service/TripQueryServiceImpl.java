package com.trip.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.dto.Response;
import com.common.exception.CustomException;
import com.common.exception.ErrorCode;
import com.trip.entity.TripPlanEntity;
import com.trip.mapper.TripQueryMapper;
import com.trip.vo.TripRequest.SearchFilter;
import com.trip.vo.TripResponse.TripPlanDetail;
import com.trip.vo.TripResponse.TripPlanReply;
import com.trip.vo.TripResponse.TripPlanResponse;
import com.trip.vo.TripResponse;
import com.user.entity.User;
import com.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TripQueryServiceImpl implements TripQueryService {

	private final TripQueryMapper tripQueryMapper;
	private final UserMapper userMapper;
	private final Response response;

	@Override
	public ResponseEntity<?> searchAttraction(SearchFilter searchFilter) {
		List<TripResponse.AttractionInfo> resultList = tripQueryMapper.searchAttraction(searchFilter).stream()
				.map(tripInfoEntity -> TripResponse.AttractionInfo.from(tripInfoEntity)).collect(Collectors.toList());

		if (resultList.size() == 0) {
			return response.success(resultList, "no content", HttpStatus.NO_CONTENT);
		}

		return response.success(resultList, "검색 성공", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getTripPlan(String userEmail) {
		User user = userMapper.selectByEmail(userEmail);

		if (user == null) {
			return response.fail("유저가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		try {
			List<TripResponse.TripPlan> selectedList = tripQueryMapper.getSelectedTrip(user.getId()).stream()
					.map(entity -> TripResponse.TripPlan.from(entity, true)).collect(Collectors.toList());

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("userId", user.getId());
			paramMap.put("userCode", user.getUserCode());

			List<TripResponse.TripPlan> unSelectedList = tripQueryMapper.getUnSelectedTrip(paramMap).stream()
					.map(entity -> TripResponse.TripPlan.from(entity, false)).toList();

			TripPlanResponse result = new TripPlanResponse(selectedList, unSelectedList);

			return response.success(result, "여행 목록 조회 성공", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return response.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> getTripDetail(String planId, String userEmail) {
		try {
			User user = userMapper.selectByEmail(userEmail);
			TripPlanEntity entity = tripQueryMapper.selectByPlanId(planId);
			
			List<String> members = validateUser(planId, user, entity);
			List<Integer> dayList = tripQueryMapper.selectDay(planId);
			
			List<List<TripResponse.AttractionInfo>> result = new ArrayList<>();
			
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("planId", planId);
			
			for(int day: dayList) {
				paramMap.put("day", day);
				
				List<com.attraction.entity.AttractionInfo> tempList = tripQueryMapper.selectByPlanIdAndDay(paramMap);
				
				if(tempList == null) {
					result.add(new ArrayList<>());
					continue;
				}
				
				List<TripResponse.AttractionInfo> element = tripQueryMapper.selectByPlanIdAndDay(paramMap).stream()
						.map(TripResponse.AttractionInfo::from)
						.toList();
				result.add(element);
			}
			
			TripPlanDetail plan = TripPlanDetail.from(entity, result, members);
			
			return response.success(plan, "계획 상세 조회 성공", HttpStatus.OK);
		} catch (Exception e) {
			return response.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> getAllReplies(String planId, String userEmail) {
		List<TripPlanReply> resultList = tripQueryMapper.selectAllReplies(planId).stream()
				.map(TripPlanReply::from)
				.toList();
		
		return response.success(resultList, "댓글 정보 조회 성공", HttpStatus.OK);
	}
	
	private List<String> validateUser(String planId, User userEntity, TripPlanEntity planEntity) {
		if(planEntity == null) {
			throw new CustomException(ErrorCode.PLAN_NOT_EXISTS);
		}
		
		List<String> members = tripQueryMapper.getMemberByPlanId(planId);
		Set<String> memberSet = new HashSet<>(members);
		
		if(planEntity.getUserId() != userEntity.getId() && !memberSet.contains(userEntity.getEmail())) {
			throw new CustomException(ErrorCode.ILLEGAL_USER_ACCESS);
		}
		
		return members;
	}

}
