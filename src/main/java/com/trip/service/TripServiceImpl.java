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
		} catch (CustomException e) {
			return response.fail(e.getMessage(), e.getStatus());
		}

		return response.success("성공");
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
			
			log.info(String.valueOf(tripPlanId));

			try {
				tripMapper.insertPlaces(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException(ErrorCode.MY_BATIS_EXCEPTION);
			}

			day++;
		}

	}

	private void validateUserCode(List<String> members) {
		if (userCodeSet == null) {
			userCodeSet = new HashSet<String>(userMapper.selectAllUserCode());
		}

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
}
