package it.polito.tdp.poweroutages.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		List<PowerOutage> soluzione=model.calcolaWorstCase(model.getNercList().get(2),4 , 200);
		for(PowerOutage p: soluzione) {
			System.out.println(p+"\n");
		}

	}

}
