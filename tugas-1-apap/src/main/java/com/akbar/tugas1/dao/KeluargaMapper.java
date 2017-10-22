package com.akbar.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.akbar.tugas1.model.KeluargaModel;
import com.akbar.tugas1.model.PendudukModel;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Many;


@Mapper
public interface KeluargaMapper {
	@Select ("select k.id,k.nomor_kk, k.alamat, k.rt,k.rw,kl.nama_kelurahan,kc.nama_kecamatan,ko.nama_kota "
			+ "from keluarga k, kelurahan kl, kecamatan kc, kota ko where k.id_kelurahan = kl.id and kl.id_kecamatan = kc.id "
			+ "and kc.id_kota = ko.id and k.nomor_kk = #{nkk} ")
	@Results (value = {
		@Result (property = "id" , column = "id" ),
		@Result (property = "nkk" , column = "nomor_kk" ),
		@Result (property = "alamat" , column = "alamat" ),
		@Result (property = "rt" , column = "rt" ),
		@Result (property = "rw" , column = "rw" ),
		@Result (property = "kelurahan" , column = "nama_kelurahan" ),
		@Result (property = "kecamatan" , column = "nama_kecamatan" ),
		@Result (property = "kota" , column = "nama_kota" ),
		@Result (property = "anggotaKeluarga" , column = "id", javaType = List.class,
		many=@Many (select = "selectPenduduks"))
	})
	KeluargaModel selectKeluarga(@Param ("nkk") String nkk);
	
	@Insert ("INSERT INTO `keluarga`(`nomor_kk`, `alamat`, `RT`, `RW`, `id_kelurahan`, `is_tidak_berlaku`) VALUES "
			+ "(#{nkk},#{alamat},#{rt},#{rw},#{idKelurahan},#{isTdkBerlaku})")
	void insertKeluarga(KeluargaModel keluarga);

	@Select ("SELECT kode_kecamatan FROM kecamatan WHERE nama_kecamatan = #{kecamatan}")
	String getKodeKecamatan(@Param ("kecamatan") String kecamatan);
	
	@Select ("SELECT nomor_kk from keluarga where nomor_kk like #{nkk} order by id desc limit 1")
	String nkkLast(@Param ("nkk") String nkk);
	
	@Select ("SELECT p.nik, p.nama, p.tempat_lahir, p.tanggal_lahir, p.jenis_kelamin, p.is_wni, p.id_keluarga, p.agama, "
			+ "p.pekerjaan, p.status_perkawinan, p.status_dalam_keluarga, p.golongan_darah,p.is_wafat "
			+ "from penduduk p, keluarga k where p.id_keluarga = k.id and k.id = #{id}")
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
	List<PendudukModel> selectPenduduks (@Param ("id") String id);
	
	@Select ("SELECT id FROM kelurahan WHERE nama_kelurahan = #{kelurahan}")
	String selectKelurahanByName(@Param ("kelurahan") String kelurahan);
	
	//JIKA PARAMETER ADALAH ID
	@Select ("SELECT kode_kelurahan FROM kelurahan WHERE id = #{id}")
	String getKodeKelurahan(@Param ("id") String id);
		
	@Update ("UPDATE `keluarga` SET `nomor_kk`=#{keluarga.nkk},`alamat`= #{keluarga.alamat},"
			+ "`RT`=#{keluarga.rt},`RW`=#{keluarga.rw},`id_kelurahan`= #{keluarga.idKelurahan},`is_tidak_berlaku`= #{keluarga.isTdkBerlaku} "
			+ "WHERE `nomor_kk`=#{nomor_kk}")
	void updateKeluarga(@Param("keluarga") KeluargaModel keluarga, @Param("nomor_kk") String nkk);
	
	@Select("SELECT `id`,`nomor_kk`, `alamat`, `RT`, `RW`, `id_kelurahan`, `is_tidak_berlaku` FROM `keluarga` WHERE nomor_kk = #{nkk}")
	@Results (value = {
		@Result (property = "id" , column = "id" ),
		@Result (property = "nkk" , column = "nomor_kk" ),
		@Result (property = "alamat" , column = "alamat" ),
		@Result (property = "rt" , column = "rt" ),
		@Result (property = "rw" , column = "rw" ),
		@Result (property = "idKelurahan" , column = "id_kelurahan" ),
		@Result (property = "isTdkBerlaku" , column = "is_tidak_berlaku" )
	})
	KeluargaModel selectKeluargaTable(@Param("nkk") String nkk);

	@Update ("UPDATE keluarga SET is_tidak_berlaku = 1 WHERE id=#{id}")
	void updateTidakBerlaku(@Param("id") String id);
}