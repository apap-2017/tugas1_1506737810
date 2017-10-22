package com.akbar.tugas1.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	String id;
	String nkk;
	@NotNull
	String alamat;
	@NotNull
	String rt;
	@NotNull
	String rw;
	@NotNull
	String idKelurahan;
	String kelurahan;
	String kecamatan;
	String kota;
	@NotNull
	String isTdkBerlaku;
	List<PendudukModel> anggotaKeluarga;
}