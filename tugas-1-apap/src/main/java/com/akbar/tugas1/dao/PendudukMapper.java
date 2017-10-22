package com.akbar.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.akbar.tugas1.model.PendudukModel;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

@Mapper
public interface PendudukMapper {
	@Select("SELECT p.nik,p.nama, p.jenis_kelamin, p.tanggal_lahir from penduduk p, keluarga k where p.id_keluarga = k.id and k.id_kelurahan = #{id}")
	@Results (value = {
			@Result (property = "nik" , column = "nik" ),
			@Result (property = "nama" , column = "nama" ),
			@Result (property = "tglLahir" , column = "tanggal_lahir" ),
			@Result (property = "jenisKelamin" , column = "jenis_kelamin" )
		})
	List<PendudukModel> selectPendudukByIdKelurahan (@Param("id") String idKelurahan);
	
	@Select ("select p.nik,p.nama,p.tempat_lahir,p.tanggal_lahir,k.alamat,k.rt,k.rw,kl.nama_kelurahan,"
			+ "kc.nama_kecamatan, ko.nama_kota, p.golongan_darah, p.agama, p.status_perkawinan, p.pekerjaan, p.is_wni, p.is_wafat "
			+ "from penduduk p, keluarga k, kelurahan kl,kecamatan kc,kota ko where p.id_keluarga = k.id and "
			+ "k.id_kelurahan = kl.id and kl.id_kecamatan = kc.id and kc.id_kota = ko.id and p.nik = #{nik}")
	@Results (value = {
		@Result (property = "nik" , column = "nik" ),
		@Result (property = "nama" , column = "nama" ),
		@Result (property = "tempatLahir" , column = "tempat_lahir" ),
		@Result (property = "tglLahir" , column = "tanggal_lahir" ),
		@Result (property = "alamat" , column = "alamat" ),
		@Result (property = "rt" , column = "rt" ),
		@Result (property = "rw" , column = "rw" ),
		@Result (property = "kelurahan" , column = "nama_kelurahan" ),
		@Result (property = "kecamatan" , column = "nama_kecamatan" ),
		@Result (property = "kota" , column = "nama_kota" ),
		@Result (property = "golonganDarah" , column = "golongan_darah" ),
		@Result (property = "agama" , column = "agama" ),
		@Result (property = "statusPerkawinan" , column = "status_perkawinan" ),
		@Result (property = "pekerjaan" , column = "pekerjaan" ),
		@Result (property = "isWNI" , column = "is_wni" ),
		@Result (property = "isWafat" , column = "is_wafat" )
	})
	PendudukModel selectPenduduk (@Param ("nik") String nik);
	
	@Select ("SELECT `nik`, `nama`, `tempat_lahir`, `tanggal_lahir`, `jenis_kelamin`, `is_wni`, `id_keluarga`, `agama`, "
			+ "`pekerjaan`, `status_perkawinan`, `status_dalam_keluarga`, `golongan_darah`, `is_wafat` FROM `penduduk` WHERE nik = #{nik}")
	@Results (value = {
		@Result (property = "nik" , column = "nik" ),
		@Result (property = "nama" , column = "nama" ),
		@Result (property = "tempatLahir" , column = "tempat_lahir" ),
		@Result (property = "tglLahir" , column = "tanggal_lahir" ),
		@Result (property = "jenisKelamin" , column = "jenis_kelamin" ),
		@Result (property = "isWNI" , column = "is_wni" ),
		@Result (property = "idKeluarga" , column = "id_keluarga" ),
		@Result (property = "agama" , column = "agama" ),
		@Result (property = "statusPerkawinan" , column = "status_perkawinan" ),
		@Result (property = "pekerjaan" , column = "pekerjaan" ),
		@Result (property = "golonganDarah" , column = "golongan_darah" ),
		@Result (property = "statusDlmKeluarga" , column = "status_dalam_keluarga" ),
		@Result (property = "isWafat" , column = "is_wafat" )
	})
	PendudukModel selectPendudukTable (@Param ("nik") String nik);
		
	@Insert ("INSERT INTO `penduduk`(`nik`, `nama`, `tempat_lahir`, `tanggal_lahir`, `jenis_kelamin`, "
			+ "`is_wni`, `id_keluarga`, `agama`, `pekerjaan`, `status_perkawinan`, `status_dalam_keluarga`, "
			+ "`golongan_darah`, `is_wafat`) VALUES (#{nik},#{nama},#{tempatLahir},#{tglLahir},#{jenisKelamin},#{isWNI},"
			+ "#{idKeluarga},#{agama},#{pekerjaan},#{statusPerkawinan},#{statusDlmKeluarga},#{golonganDarah},#{isWafat})")
	void insertPenduduk(PendudukModel penduduk);
	
	@Select ("select distinct kl.kode_kelurahan from kelurahan kl, keluarga k where kl.id = k.id_kelurahan and k.id = #{id}")
	String getKodeLurah(@Param("id") String id);
	
	@Select ("select nik from penduduk where nik like #{nik} order by nik desc limit 1")
	String nikLastSame(@Param("nik") String nik);
	
	@Update ("UPDATE `penduduk` SET `nik`=#{penduduk.nik},`nama`=#{penduduk.nama},`tempat_lahir`=#{penduduk.tempatLahir},"
			+ "`tanggal_lahir`=#{penduduk.tglLahir},"
			+ "`jenis_kelamin`=#{penduduk.jenisKelamin},`is_wni`= #{penduduk.isWNI},`id_keluarga`= #{penduduk.idKeluarga},`agama`=#{penduduk.agama},"
			+ "`pekerjaan`=#{penduduk.pekerjaan},`status_perkawinan`= #{penduduk.statusPerkawinan},"
			+ "`status_dalam_keluarga`=#{penduduk.statusDlmKeluarga},`golongan_darah`= #{penduduk.golonganDarah},`is_wafat`=#{penduduk.isWafat} "
			+ "WHERE nik = #{nomor_ik}")
	void updatePenduduk (@Param("penduduk") PendudukModel penduduk, @Param("nomor_ik") String nomor_ik);
	
	@Update("UPDATE `penduduk` SET `is_wafat` = '1' WHERE `nik` = #{nik}")
	void updateWafat(@Param("nik") String nik);
}