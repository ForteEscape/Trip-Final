package com.hotplace.util;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.stereotype.Component;

import com.hotplace.entity.HotPlaceRecommendEntity;

@Component
public class WeightCalculator {

	public double getWeight(List<HotPlaceRecommendEntity> recommendList) {
		return calcWeight(recommendList);
	}

	private double calcWeight(List<HotPlaceRecommendEntity> recommendList) {
		StringTokenizer st = new StringTokenizer(recommendList.get(0).getRecommendTime());
		st = new StringTokenizer(st.nextToken(), "-");

		Calendar constant = Calendar.getInstance();
		Calendar var = Calendar.getInstance();

		double cons = constant.getTimeInMillis();
		double sum = 0;

		for (int i = 0; i < recommendList.size(); i++) {
			st = new StringTokenizer(recommendList.get(i).getRecommendTime());
			st = new StringTokenizer(st.nextToken(), "-");
			var.clear();
			var.set(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) - 1,
					Integer.parseInt(st.nextToken()));

			double temp = var.getTimeInMillis();
			double exp = (cons - temp) / (60 * 60 * 1000 * 24);
			double interm = Math.pow(0.8, exp);

			sum += interm;
		}
		sum *= recommendList.size();

		return sum;
	}

}
