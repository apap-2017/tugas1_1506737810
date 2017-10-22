package com.akbar.tugas1.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel {
	String id;
	@NotNull
	private String nik;
	@NotNull
	private String nama;
	@NotNull
	private String tempatLahir;
	@NotNull
	private String tglLahir;
	private String alamat;
	private String rt;
	private String rw;
	private String kelurahan;
	private String kecamatan;
	private String kota;
	@NotNull
	private String golonganDarah;
	@NotNull
	private String agama;
	@NotNull
	private String statusPerkawinan;
	@NotNull
	private String pekerjaan;
	@NotNull
	private String isWNI;
	@NotNull
	private String isWafat;
	@NotNull
	private String jenisKelamin;
	@NotNull
	private String idKeluarga;
	@NotNull
	private String statusDlmKeluarga;
}