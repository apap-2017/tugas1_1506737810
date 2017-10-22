package com.akbar.tugas1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanModel {
	String id;
	String idKota;
	String idKecamatan;
	String namaKelurahan;
	String namaKota;
	String namaKecamatan;
	String idKelurahan;
}
