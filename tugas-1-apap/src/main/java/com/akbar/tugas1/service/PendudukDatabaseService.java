package com.akbar.tugas1.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akbar.tugas1.dao.PendudukMapper;
import com.akbar.tugas1.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukDatabaseService implements PendudukService{
	
	@Autowired
	private PendudukMapper penduduk;
	
	@Autowired
	private KeluargaService keluargaService;
	
	public PendudukModel selectPenduduk(String nik) {
		log.info ("select penduduk with nik {}", nik);
		PendudukModel pend = penduduk.selectPenduduk(nik);	
		
		SimpleDateFormat month_date = new SimpleDateFormat("dd,MMM yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(pend.getTglLahir());
			pend.setTglLahir(month_date.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return pend;
	}

	@Override
	public void insertPenduduk(PendudukModel pend) {
		String kodeKecamatan = getKodeLurah(pend.getIdKeluarga()).substring(0,6);
		String tglLahir = pend.getTglLahir();
		String kodeLahir = null;
		if (pend.getJenisKelamin().equalsIgnoreCase("1")) {
			int tambah = Integer.parseInt(tglLahir.substring(8,10)) + 40;
			kodeLahir = tambah + tglLahir.substring(5,7) + tglLahir.substring(2,4);
		} else {
			kodeLahir = tglLahir.substring(8,10) + tglLahir.substring(5,7) + tglLahir.substring(2,4);
		}
		String nikLast = nikLastSame(kodeKecamatan + kodeLahir + '%');
		if(nikLast != null) {
			nikLast = Long.toString(Long.parseLong (nikLast) + 1);
		} else {
			nikLast = kodeKecamatan + kodeLahir + "0001";
		}
		pend.setNik(nikLast);
		penduduk.insertPenduduk(pend);
		log.info("insert penduduk with nik {}", pend.getNik());
	}
	
	public String nikLastSame(String nik) {
		return penduduk.nikLastSame(nik);
	}
	
	public String getKodeLurah(String id) {
		return penduduk.getKodeLurah(id);
	}

	@Override
	public void updatePenduduk(PendudukModel pend, String nomor_ik) {
		String kodeKecamatan = getKodeLurah(pend.getIdKeluarga()).substring(0,6);
		String tglLahir = pend.getTglLahir();
		String kodeLahir = null;
		if (pend.getJenisKelamin().equalsIgnoreCase("1")) {
			int tambah = Integer.parseInt(pend.getTglLahir().substring(8,10)) + 40;
			kodeLahir = tambah + tglLahir.substring(5,7) + tglLahir.substring(2,4);
		} else {
			kodeLahir = tglLahir.substring(8,10) + tglLahir.substring(5,7) + tglLahir.substring(2,4);
		}
		String nikLast = nikLastSame(kodeKecamatan + kodeLahir + '%');
		if(nikLast != null && !((kodeKecamatan+kodeLahir).equals(nomor_ik.substring(0,12)))) {
			nikLast = Long.toString(Long.parseLong (nikLast) + 1);
		} else {
			nikLast = kodeKecamatan + kodeLahir + "0001";
		}
		pend.setNik(nikLast);
		penduduk.updatePenduduk(pend,nomor_ik);
		log.info("update penduduk with nik {}", nomor_ik);
	}

	@Override
	public PendudukModel selectPendudukTable(String nik) {
		log.info ("select penduduk with nik {}", nik);
        return penduduk.selectPendudukTable(nik);
	}

	@Override
	public void updateWafat(String nik) {
		penduduk.updateWafat(nik);
		PendudukModel orang = penduduk.selectPendudukTable(nik);
		keluargaService.updateTidakBerlaku(orang.getIdKeluarga());
		log.info("penduduk wafat dengan nik = {}", nik);
	}

	@Override
	public List<PendudukModel> selectPendudukByIdKelurahan(String id) {
		log.info("select semua penduduk dengan id kelurahan = {}", id);
		List<PendudukModel> list = penduduk.selectPendudukByIdKelurahan(id);
		for(int i=0; i < list.size() ; i++)
		{
			if(list.get(i).getJenisKelamin().equals("1")) {
				list.get(i).setJenisKelamin("Perempuan");
			} else {
				list.get(i).setJenisKelamin("Laki-laki");
			}
		}
		return list; 
	}

	@Override
	public PendudukModel orangTertua(List<PendudukModel> listPenduduk) {
		PendudukModel tua = listPenduduk.get(0);
		for(int i = 0; i <listPenduduk.size();i++)
		{
			if(listPenduduk.get(i).getTglLahir().compareTo(tua.getTglLahir()) < 0)
			{
				tua = listPenduduk.get(i);
			}
		}
		return tua;
	}

	@Override
	public PendudukModel orangTermuda(List<PendudukModel> listPenduduk) {
		PendudukModel muda = listPenduduk.get(0);
		for(int i = 0; i <listPenduduk.size();i++)
		{
			if(muda.getTglLahir().compareTo(listPenduduk.get(i).getTglLahir()) < 0)
			{
				muda = listPenduduk.get(i);
			}
		}
		return muda;
	}
}