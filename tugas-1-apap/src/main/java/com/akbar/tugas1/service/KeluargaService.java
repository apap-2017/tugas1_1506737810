package com.akbar.tugas1.service;

import com.akbar.tugas1.model.KeluargaModel;

public interface KeluargaService {

	KeluargaModel selectKeluarga(String nomorKK);
	
	void updateKeluarga(KeluargaModel keluarga, String nkk);
	void insertKeluarga(KeluargaModel keluarga);
	String getKodeKecamatan (String kecamatan);
	String nkkLast(String nkk);
	
	String selectKelurahanByName (String kelurahan);
	String getKodeKelurahan(String idKelurahan);
	KeluargaModel selectKeluargaTable (String nomorKK);
	void updateTidakBerlaku(String id);
}
