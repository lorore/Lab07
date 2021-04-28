package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO podao;
	private int x;
	private int y;
	private int max;
	private List<PowerOutage> soluzione;
	private List<PowerOutage> listaCompleta;
	
	public Model() {
		podao = new PowerOutageDAO();
		
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> calcolaWorstCase(Nerc n, int x, int y) {
		this.x=x;
		this.y=y;
		this.max=0;
		List<PowerOutage> parziale=new ArrayList<>();
		listaCompleta=this.podao.getAllPowerOutages(n);
		this.trovaSequenza(parziale, 0);
		//System.out.println(this.calcolaTempoTotale(soluzione));
		//System.out.println(this.calcolaTotalePersone(soluzione));
		Collections.sort(soluzione);
		return this.soluzione;
	}
	
	private void trovaSequenza(List<PowerOutage> parziale, int livello) {
		
		long diffAnni=this.calcolaDiffAnni(parziale);
	//	System.out.println("Diff anni "+diffAnni);
		
		if(diffAnni>x) {
			return;
		}
		long tempoTotale=this.calcolaTempoTotale(parziale);
	//	System.out.println("tempoTot "+tempoTotale);
		
		if(tempoTotale>y) {
			return;
		}
		
		int totPersone=this.calcolaTotalePersone(parziale);
		//System.out.println("totPersone "+totPersone);
		if(totPersone>this.max) {
			max=totPersone;
			this.soluzione=new ArrayList<PowerOutage>(parziale);
		//	System.out.println("max: "+max+"\n"+"soluzione: "+soluzione);
			return;
		}
		//è necessario questo controllo?
		if(livello==listaCompleta.size()) {
			return;
		}
		else {
		for(int i=0; i<listaCompleta.size(); i++) {
			PowerOutage p=listaCompleta.get(i);
			if(!parziale.contains(p)) {
			parziale.add(p);
		//	System.out.println(parziale);
			trovaSequenza(parziale, livello+1);
			parziale.remove(p);
		//	System.out.println(parziale);
			}
		}
		}
	}
	
	private long calcolaDiffAnni(List<PowerOutage> parziale) {
		LocalDateTime tMax=LocalDateTime.of(1999, 9, 25, 9, 10, 10);
		LocalDateTime tMin=LocalDateTime.of(2015,9,25,9,10,10);
		
		
		for(PowerOutage p: parziale) {
			if(p.getInizio().isBefore(tMin)) {
				tMin=p.getInizio();
			}
			
			if(p.getInizio().isAfter(tMax)) {
				tMax=p.getInizio();
			}
			
			
		}
		
		long diff=tMin.until(tMax, ChronoUnit.YEARS);
		
		return diff;
		
		
	}
	
	public long calcolaTempoTotale(List<PowerOutage> parziale) {
		
		long somma=0;
		for(PowerOutage p: parziale) {
			somma+= p.getInizio().until(p.getFine(), ChronoUnit.HOURS);
		}
		
		return somma;
	}
	
	public int calcolaTotalePersone(List<PowerOutage> parziale) {
		int somma=0;
		for(PowerOutage p: parziale) {
			somma+=p.getCustomers_affected();
		}
		return somma;
	}
	

}
