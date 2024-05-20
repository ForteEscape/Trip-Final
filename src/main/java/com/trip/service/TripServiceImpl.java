package com.trip.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import com.trip.mapper.TripMapper;
import com.trip.vo.TripRequest.SearchFilter;
import com.trip.vo.TripRequest.TripPlan;
import com.trip.vo.TripResponse;
import com.trip.vo.TripResponse.TripPlanResponse;
import com.user.entity.User;
import com.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripServiceImpl implements TripService {

	private final TripMapper tripMapper;
	private final UserMapper userMapper;
	private final Response response;
	private Set<String> userCodeSet;

	@Transactional(readOnly = true)
	@Override
	public ResponseEntity<?> searchAttraction(SearchFilter searchFilter) {
		List<TripResponse.AttractionInfo> resultList = tripMapper.searchAttraction(searchFilter).stream()
				.map(tripInfoEntity -> TripResponse.AttractionInfo.from(tripInfoEntity)).collect(Collectors.toList());

		if (resultList.size() == 0) {
			return response.success(resultList, "no content", HttpStatus.NO_CONTENT);
		}

		return response.success(resultList, "검색 성공", HttpStatus.OK);
	}

	@Transactional
	@Override
	public ResponseEntity<?> addTripPlan(TripPlan tripPlan, String userEmail) {
		try {
			User user = userMapper.selectByEmail(userEmail);

			validatePlanDate(tripPlan.startDate(), tripPlan.endDate());
			validateUserCode(tripPlan.members());

			TripPlanEntity entity = TripPlanEntity.from(tripPlan);

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("user", user);
			paramMap.put("trip", entity);

			tripMapper.insertTripPlan(paramMap);

			insertVisitPlace(tripPlan.places(), entity.getId());
			insertMembers(tripPlan.members(), entity.getId());
			insertSelectedTrip(entity.getId(), user.getId());
		} catch (CustomException e) {
			return response.fail(e.getMessage(), e.getStatus());
		}

		return response.success("성공");
	}

	@Transactional
	private void insertSelectedTrip(int planId, int userId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("planId", planId);
		paramMap.put("userId", userId);

		tripMapper.insertSelectedTrip(paramMap);
	}

	@Transactional
	private void insertMembers(List<String> members, int id) {
		Map<String, Object> paramMap = new HashMap<>();

		paramMap.put("planId", id);
		paramMap.put("members", members);

		tripMapper.insertTripMembers(paramMap);
	}

	@Transactional
	private void insertVisitPlace(List<List<Integer>> places, int tripPlanId) {
		Map<String, Object> paramMap = new HashMap<>();

		int day = 1;
		for (List<Integer> row : places) {
			paramMap.put("day", day);
			paramMap.put("places", row);
			paramMap.put("planId", tripPlanId);

			try {
				tripMapper.insertPlaces(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException(ErrorCode.MY_BATIS_EXCEPTION);
			}

			day++;
		}

	}

	@Transactional(readOnly = true)
	@Override
	public ResponseEntity<?> getTripPlan(String userEmail) {
		User user = userMapper.selectByEmail(userEmail);

		if (user == null) {
			return response.fail("유저가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		try {
			List<TripResponse.TripPlan> selectedList = tripMapper.getSelectedTrip(user.getId()).stream()
					.map(entity -> TripResponse.TripPlan.from(entity, true))
					.collect(Collectors.toList());
			
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("userId", user.getId());
			paramMap.put("userCode", user.getUserCode());
			
			List<TripResponse.TripPlan> unSelectedList = tripMapper.getUnSelectedTrip(paramMap).stream()
					.map(entity -> TripResponse.TripPlan.from(entity, false))
					.toList();
			
			TripPlanResponse result = new TripPlanResponse(selectedList, unSelectedList);
			
			return response.success(result, "여행 목록 조회 성공", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return response.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void validateUserCode(List<String> members) {
		userCodeSet = new HashSet<String>(userMapper.selectAllUserCode());

		if (!userCodeSet.containsAll(members)) {
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		}
	}

	private void validatePlanDate(LocalDate startDate, LocalDate endDate) {
		LocalDate now = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		if (startDate.isBefore(now)) {
			throw new CustomException(ErrorCode.INVALID_START_DATE);
		}

		if (endDate.isBefore(startDate) || endDate.isBefore(now)) {
			throw new CustomException(ErrorCode.INVALID_END_DATE);
		}
	}

	@Transactional
	@Override
	public ResponseEntity<?> selectTripPlan(String planId, String userEmail) {
		User user = userMapper.selectByEmail(userEmail);
		
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("planId", planId);
			paramMap.put("userId", user.getId());
			
			int result = tripMapper.getSelectedTripByPlanId(paramMap);
			
			if(result != 0) {
				return response.fail("이미 선택한 여행 계획입니다.", HttpStatus.BAD_REQUEST);
			}
			
			tripMapper.insertSelectedTrip(paramMap);
		} catch (Exception e) {
			return response.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response.success("선택 처리 성공");
	}

	@Transactional
	@Override
	public ResponseEntity<?> unselectTripPlan(String planId, String userEmail) {
		User user = userMapper.selectByEmail(userEmail);
		
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("planId", planId);
			paramMap.put("userId", user.getId());
			
			int result = tripMapper.getSelectedTripByPlanId(paramMap);
			
			if(result == 0) {
				return response.fail("해당 여행 계획이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
			}
			
			tripMapper.deleteSelectedTrip(paramMap);
		} catch (Exception e) {
			return response.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response.success("선택 취소 처리 성공");
	}

}
