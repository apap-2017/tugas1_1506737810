package com.akbar.tugas1.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akbar.tugas1.dao.KeluargaMapper;
import com.akbar.tugas1.model.KeluargaModel;
import com.akbar.tugas1.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaDatabaseService implements KeluargaService {
	@Autowired
	private KeluargaMapper keluarga;
	
	@Autowired
	private PendudukService pendudukService;
	
	@Override
	public KeluargaModel selectKeluarga(String nomorKK) {
		log.info("select keluarga with nkk {}", nomorKK);
		KeluargaModel kel = keluarga.selectKeluarga(nomorKK);
		for(int i = 0; i < kel.getAnggotaKeluarga().size();i++)
		{
			SimpleDateFormat month_date = new SimpleDateFormat("dd,MMMMMM yyyy", Locale.ENGLISH);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			try {
				date = sdf.parse(kel.getAnggotaKeluarga().get(i).getTglLahir());
				kel.getAnggotaKeluarga().get(i).setTglLahir((month_date.format(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(kel.getAnggotaKeluarga().get(i).getJenisKelamin().equals("1")) {
				kel.getAnggotaKeluarga().get(i).setJenisKelamin("Perempuan");
			} else {
				kel.getAnggotaKeluarga().get(i).setJenisKelamin("Laki-laki");
			}
			
			if(kel.getAnggotaKeluarga().get(i).getIsWNI().equals("1")) {
				kel.getAnggotaKeluarga().get(i).setIsWNI("WNI");
			} else {
				kel.getAnggotaKeluarga().get(i).setIsWNI("WNA");
			}
		}
		return kel;
	}
	
	@Override
	public KeluargaModel selectKeluargaTable(String nomorKK) {
		log.info("select keluarga with nkk {}", nomorKK);
		return keluarga.selectKeluargaTable(nomorKK);
	}

	@Override
	public void insertKeluarga(KeluargaModel kel) {
		String kodeKecamatan = getKodeKecamatan(kel.getKecamatan()).substring(0,6);
		
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    String today = formatter.format(date);
	    String kodeWaktu = today.substring(0,2) + today.substring(3,5) + today.substring(8,10);
	    String kode = nkkLast(kodeKecamatan + kodeWaktu + '%');
	    if (kode != null) {
	    	kode = Long.toString(Long.parseLong (kode) + 1);
	    } else {
	    	kode = kodeKecamatan + kodeWaktu + "0001";
	    }
	    String idKel = keluarga.selectKelurahanByName(kel.getKelurahan());
	    kel.setIdKelurahan(idKel);
	    kel.setNkk(kode);
	    System.out.println(kel.getNkk());
	    kel.setIsTdkBerlaku("0");
	    keluarga.insertKeluarga(kel);
	}

	@Override
	public String getKodeKecamatan(String kecamatan) {
		log.info("get kode kecamatan with nama kecamatan = {}", kecamatan);
		return keluarga.getKodeKecamatan(kecamatan);
	}
	
	@Override
	public String nkkLast(String nkk) {
		// TODO Auto-generated method stub
		return keluarga.nkkLast(nkk);
	}

	@Override
	public String selectKelurahanByName(String kelurahan) {
		log.info("select kelurahan with nama_kelurahan = {}", kelurahan);
		return keluarga.selectKelurahanByName(kelurahan);
	}

	@Override
	public void updateKeluarga(KeluargaModel kel, String nkk) {
		String kodeKelurahan = getKodeKelurahan(kel.getIdKelurahan()).substring(0,6);
		String nomorKK = null;
		boolean isChanged = false;
		List<PendudukModel> pendudukUpdate = null;
		if(!(kodeKelurahan.equals(nkk.substring(0,6)))) {
			nomorKK = kodeKelurahan + nkk.substring(6,12);
			nomorKK = keluarga.nkkLast(nomorKK+"%");
			if(nomorKK != null) {
				nomorKK = Long.toString(Long.parseLong (nomorKK) + 1); 
			} else {
				nomorKK = kodeKelurahan + nkk.substring(6,12) + "0001";
			}
			isChanged = true;
			pendudukUpdate = keluarga.selectPenduduks(kel.getId());
		} else {
			nomorKK = nkk;
		}
		kel.setNkk(nomorKK);
		keluarga.updateKeluarga(kel, nkk);
		if(isChanged) {
			for(int i = 0; i < pendudukUpdate.size(); i++) {
				pendudukService.updatePenduduk(pendudukUpdate.get(i),pendudukUpdate.get(i).getNik());
			}
		}
		log.info("update keluarga with = {}", kel.getNkk());
	}

	@Override
	public String getKodeKelurahan(String idKelurahan) {
		log.info("get kode keluarahan with id = {}", idKelurahan);
		return keluarga.getKodeKelurahan(idKelurahan);
	}

	@Override
	public void updateTidakBerlaku(String id) {
		List<PendudukModel> anggotaKeluarga = keluarga.selectPenduduks(id);
		boolean semuaWafat = true;
		for(int i = 0; i < anggotaKeluarga.size(); i++) {
			if(anggotaKeluarga.get(i).getIsWafat().equals("0")) {
				semuaWafat = false;
			}
		}
		if(semuaWafat) {
			keluarga.updateTidakBerlaku(id);
			log.info("update nkk menjadi tidak berlaku dengan id keluarga = {}", id);
		}
		log.info("nkk tetap berlaku dengan id keluarga = {}", id);
	}
}
