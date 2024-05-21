package com.trip.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.dto.Response;
import com.common.exception.CustomException;
import com.common.exception.ErrorCode;
import com.trip.entity.TripPlanEntity;
import com.trip.entity.TripPlanReplyEntity;
import com.trip.mapper.TripCommandMapper;
import com.trip.mapper.TripQueryMapper;
import com.trip.vo.TripRequest.TripPlan;
import com.trip.vo.TripRequest.TripReply;
import com.user.entity.User;
import com.user.mapper.UserMapper;
import com.user.vo.RoleType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripCommandServiceImpl implements TripCommandService {

	private final TripCommandMapper tripMapper;
	private final TripQueryMapper tripQueryMapper;
	private final UserMapper userMapper;
	private final Response response;
	private Set<String> userCodeSet;

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
			e.printStackTrace();
			return response.fail(e.getMessage(), e.getStatus());
		}

		return response.success("성공");
	}
	
	@Transactional
	@Override
	public ResponseEntity<?> selectTripPlan(String planId, String userEmail) {
		User user = userMapper.selectByEmail(userEmail);
		
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("planId", planId);
			paramMap.put("userId", user.getId());
			
			int result = tripQueryMapper.getSelectedTripByPlanId(paramMap);
			
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
			
			int result = tripQueryMapper.getSelectedTripByPlanId(paramMap);
			
			if(result == 0) {
				return response.fail("해당 여행 계획이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
			}
			
			tripMapper.deleteSelectedTrip(paramMap);
		} catch (Exception e) {
			return response.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response.success("선택 취소 처리 성공");
	}
	
	@Transactional
	@Override
	public ResponseEntity<?> deleteTripPlan(String planId, String userEmail) {
		User user = userMapper.selectByEmail(userEmail);
		TripPlanEntity entity = tripQueryMapper.selectByPlanId(planId);
		
		if(!user.getRole().equals(RoleType.ADMIN.getRole())) {
			if(user.getId() != entity.getUserId()) {
				return response.fail("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
			}
		}
		
		tripMapper.deleteTripPlan(planId);
		
		return response.success("여행 계획 삭제 완료");
	}
	
	@Transactional
	@Override
	public ResponseEntity<?> createNewReply(String planId, TripReply reply, String userEmail) {
		try {
			User user = userMapper.selectByEmail(userEmail);
			TripPlanEntity entity = tripQueryMapper.selectByPlanId(planId);
			
			validateUser(planId, user, entity);
			
			TripPlanReplyEntity replyEntity = TripPlanReplyEntity.builder()
					.planId(entity.getId())
					.content(reply.content())
					.userId(user.getId())
					.build();
			
			tripMapper.insertReply(replyEntity);
		} catch (CustomException e) {
			return response.fail(e.getMessage(), e.getStatus());
		} catch (Exception e) {
			return response.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response.success("댓글 작성에 성공했습니다.");
	}
	
	@Transactional
	@Override
	public ResponseEntity<?> deleteReply(String planId, String replyId, String userEmail) {
		User user = userMapper.selectByEmail(userEmail);
		
		if(user == null) {
			return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
		}
		
		TripPlanReplyEntity entity = tripQueryMapper.getReplyByIdAndPlanId(replyId);
		
		if(entity.getUserId() != user.getId()) {
			return response.fail("잘못된 요청입니다. 작성자만이 삭제가 가능합니다.", HttpStatus.BAD_REQUEST);
		}
		
		tripMapper.deleteReply(replyId);
		
		return response.success("댓글 삭제 성공");
	}

	@Transactional(readOnly = true)
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

		if(members.size() != 0) {
			tripMapper.insertTripMembers(paramMap);
		}
	}

	@Transactional
	private void insertVisitPlace(List<List<Integer>> places, int tripPlanId) {
		Map<String, Object> paramMap = new HashMap<>();

		int day = 1;
		for (List<Integer> row : places) {
			if(row.size() == 0) {
				continue;
			}
			
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
}
