package com.akbar.tugas1.service;

import java.util.List;
import com.akbar.tugas1.model.PendudukModel;

public interface PendudukService {
	PendudukModel selectPenduduk(String nik);
	void insertPenduduk(PendudukModel penduduk);
	String nikLastSame(String nik);
	String getKodeLurah(String id);
	void updatePenduduk (PendudukModel penduduk, String nomor_ik);
	PendudukModel selectPendudukTable (String nik);
	void updateWafat(String nik);
	List<PendudukModel> selectPendudukByIdKelurahan(String id);
	PendudukModel orangTertua(List<PendudukModel> listPenduduk);
	PendudukModel orangTermuda(List<PendudukModel> listPenduduk);
}