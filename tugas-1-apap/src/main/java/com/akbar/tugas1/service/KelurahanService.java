package com.akbar.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.akbar.tugas1.model.KelurahanModel;

public interface KelurahanService {
	List<KelurahanModel> selectAllKota();
	List<KelurahanModel> selectKecamatanByIdKota(@Param("id") String idKota);
	List<KelurahanModel> selectKelurahanByIdKecamatan(@Param("id") String idKecamatan);
	String selectKotaById(@Param("id") String idKota);
	String selectKecamatanById(@Param("id") String idKecamatan);
	String selectKelurahanById(@Param("id") String idKelurahan);
}
