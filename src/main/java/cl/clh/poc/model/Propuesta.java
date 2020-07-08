package cl.clh.poc.model;

import java.math.BigDecimal;


public class Propuesta implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String decJurada;

	private String fecGener;

	private Integer mandt;

	private String nameFirst;

	private String nameLast;

	private String nameLst2;

	private Long numMeses;

	private BigDecimal otraRem;

	private String partner;

	private BigDecimal pensiones;

	private String periodo;

	private BigDecimal remEmp;

	private BigDecimal rentaProm;

	private BigDecimal rentasInd;

	private String rutAfiliado;

	private String rutEmpresa;

	private BigDecimal subsidios;

	private Integer tieneCarga;

	private BigDecimal totIngresos;

	private String tramo;

	private String usuario;

	private String waers;
	
	private String fecActualizacion;
	
	private String est1;
	
	private String est2;
	
	public Propuesta(String decJurada, String fecGener, Integer mandt, String nameFirst, String nameLast,
			String nameLst2, Long numMeses, BigDecimal otraRem, String partner, BigDecimal pensiones, String periodo,
			BigDecimal remEmp, BigDecimal rentaProm, BigDecimal rentasInd, String rutAfiliado, String rutEmpresa,
			BigDecimal subsidios, Integer tieneCarga, BigDecimal totIngresos, String tramo, String usuario,
			String waers, String fecActualizacion, String est1, String est2) {
		super();
		this.decJurada = decJurada;
		this.fecGener = fecGener;
		this.mandt = mandt;
		this.nameFirst = nameFirst;
		this.nameLast = nameLast;
		this.nameLst2 = nameLst2;
		this.numMeses = numMeses;
		this.otraRem = otraRem;
		this.partner = partner;
		this.pensiones = pensiones;
		this.periodo = periodo;
		this.remEmp = remEmp;
		this.rentaProm = rentaProm;
		this.rentasInd = rentasInd;
		this.rutAfiliado = rutAfiliado;
		this.rutEmpresa = rutEmpresa;
		this.subsidios = subsidios;
		this.tieneCarga = tieneCarga;
		this.totIngresos = totIngresos;
		this.tramo = tramo;
		this.usuario = usuario;
		this.waers = waers;
		this.fecActualizacion = fecActualizacion;
		this.est1 = est1;
		this.est2 = est2;
	}
	
	public Propuesta() {}
	

	public String getFecActualizacion() {
		return fecActualizacion;
	}


	public void setFecActualizacion(String fecActualizacion) {
		this.fecActualizacion = fecActualizacion;
	}


	public String getEst1() {
		return est1;
	}


	public void setEst1(String est1) {
		this.est1 = est1;
	}


	public String getEst2() {
		return est2;
	}


	public void setEst2(String est2) {
		this.est2 = est2;
	}


	public String getDecJurada() {
		return decJurada;
	}

	public void setDecJurada(String decJurada) {
		this.decJurada = decJurada;
	}

	public String getFecGener() {
		return fecGener;
	}

	public void setFecGener(String fecGener) {
		this.fecGener = fecGener;
	}

	public Integer getMandt() {
		return mandt;
	}

	public void setMandt(Integer mandt) {
		this.mandt = mandt;
	}

	public String getNameFirst() {
		return nameFirst;
	}

	public void setNameFirst(String nameFirst) {
		this.nameFirst = nameFirst;
	}

	public String getNameLast() {
		return nameLast;
	}

	public void setNameLast(String nameLast) {
		this.nameLast = nameLast;
	}

	public String getNameLst2() {
		return nameLst2;
	}

	public void setNameLst2(String nameLst2) {
		this.nameLst2 = nameLst2;
	}

	public Long getNumMeses() {
		return numMeses;
	}

	public void setNumMeses(Long numMeses) {
		this.numMeses = numMeses;
	}

	public BigDecimal getOtraRem() {
		return otraRem;
	}

	public void setOtraRem(BigDecimal otraRem) {
		this.otraRem = otraRem;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public BigDecimal getPensiones() {
		return pensiones;
	}

	public void setPensiones(BigDecimal pensiones) {
		this.pensiones = pensiones;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public BigDecimal getRemEmp() {
		return remEmp;
	}

	public void setRemEmp(BigDecimal remEmp) {
		this.remEmp = remEmp;
	}

	public BigDecimal getRentaProm() {
		return rentaProm;
	}

	public void setRentaProm(BigDecimal rentaProm) {
		this.rentaProm = rentaProm;
	}

	public BigDecimal getRentasInd() {
		return rentasInd;
	}

	public void setRentasInd(BigDecimal rentasInd) {
		this.rentasInd = rentasInd;
	}

	public String getRutAfiliado() {
		return rutAfiliado;
	}

	public void setRutAfiliado(String rutAfiliado) {
		this.rutAfiliado = rutAfiliado;
	}

	public String getRutEmpresa() {
		return rutEmpresa;
	}

	public void setRutEmpresa(String rutEmpresa) {
		this.rutEmpresa = rutEmpresa;
	}

	public BigDecimal getSubsidios() {
		return subsidios;
	}

	public void setSubsidios(BigDecimal subsidios) {
		this.subsidios = subsidios;
	}

	public Integer getTieneCarga() {
		return tieneCarga;
	}

	public void setTieneCarga(Integer tieneCarga) {
		this.tieneCarga = tieneCarga;
	}

	public BigDecimal getTotIngresos() {
		return totIngresos;
	}

	public void setTotIngresos(BigDecimal totIngresos) {
		this.totIngresos = totIngresos;
	}

	public String getTramo() {
		return tramo;
	}

	public void setTramo(String tramo) {
		this.tramo = tramo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getWaers() {
		return waers;
	}

	public void setWaers(String waers) {
		this.waers = waers;
	}

	public String[] toStringArray() {
		
		String[] ret = {periodo, rutEmpresa, rutAfiliado.split("-")[0],rutAfiliado.split("-")[1], nameLast, nameLst2, nameFirst, tramo, fecActualizacion, totIngresos.toString(), otraRem.toString(), rentasInd.toString(),subsidios.toString(), pensiones.toString(), remEmp.toString(), numMeses.toString(),rentaProm.toString(),decJurada, est1, est2};
		
		return ret;
	}
	
	

}
