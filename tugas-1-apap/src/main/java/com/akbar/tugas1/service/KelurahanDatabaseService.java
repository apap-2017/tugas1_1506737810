package com.akbar.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akbar.tugas1.dao.KelurahanMapper;
import com.akbar.tugas1.model.KelurahanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KelurahanDatabaseService implements KelurahanService{
	@Autowired
	private KelurahanMapper kelurahan;
	
	@Override
	public List<KelurahanModel> selectAllKota() {
		log.info("select semua kota");
		return kelurahan.selectAllKota();
	}

	@Override
	public List<KelurahanModel> selectKecamatanByIdKota(String idKota) {
		log.info("select semua kecamatan dengan id kota = {}",idKota);
		return kelurahan.selectKecamatanByIdKota(idKota);
	}

	@Override
	public List<KelurahanModel> selectKelurahanByIdKecamatan(String idKecamatan) {
		log.info("select kelurahan dengan id kecamatan = {}", idKecamatan);
		return kelurahan.selectKelurahanByIdKecamatan(idKecamatan);
	}

	@Override
	public String selectKotaById(String idKota) {
		log.info("select nama kota dengan id = {}", idKota);
		return kelurahan.selectKotaById(idKota);
	}

	@Override
	public String selectKecamatanById(String idKecamatan) {
		log.info("select kecamatan dengan id = {}", idKecamatan);
		return kelurahan.selectKecamatanById(idKecamatan);
	}

	@Override
	public String selectKelurahanById(String idKelurahan) {
		log.info("Select kelurahan dengan id = {}", idKelurahan);
		return kelurahan.selectKelurahanById(idKelurahan);
	}
}
