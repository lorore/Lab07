package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

public class PowerOutage implements Comparable<PowerOutage> {

	int id;
	int nercId;
	int customers_affected;
	LocalDateTime inizio;
	LocalDateTime fine;
	
	public PowerOutage(int id, int nercId, int customers_affected, LocalDateTime inizio, LocalDateTime fine) {
		super();
		this.id = id;
		this.nercId = nercId;
		this.customers_affected = customers_affected;
		this.inizio = inizio;
		this.fine = fine;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNercId() {
		return nercId;
	}

	public void setNercId(int nercId) {
		this.nercId = nercId;
	}

	public int getCustomers_affected() {
		return customers_affected;
	}

	public void setCustomers_affected(int customers_affected) {
		this.customers_affected = customers_affected;
	}

	public LocalDateTime getInizio() {
		return inizio;
	}

	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}

	public LocalDateTime getFine() {
		return fine;
	}

	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.inizio.getYear()+" "+this.inizio+" "+this.fine+" "+inizio.until(fine, ChronoUnit.HOURS)+" "+this.customers_affected;
	}

	

	
	@Override
	public int compareTo(PowerOutage o) {
		
		return (this.inizio.getYear()-o.getInizio().getYear());
	}
	
	
	
}
