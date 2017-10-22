package com.akbar.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.akbar.tugas1.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	@Select("Select id,nama_kota from kota")
	@Results (value = {
		@Result (property = "idKota" , column = "id" ),
		@Result (property = "namaKota" , column = "nama_kota" )
	})
	List<KelurahanModel> selectAllKota();
	
	@Select("select nama_kota from kota where id = #{id}")
	String selectKotaById(@Param("id") String idKota);
	
	@Select("select nama_kecamatan from kecamatan where id = #{id}")
	String selectKecamatanById(@Param("id") String idKecamatan);
	
	@Select("select nama_kelurahan from kelurahan where id = #{id}")
	String selectKelurahanById(@Param("id") String idKelurahan);

	@Select("SELECT k.id, k.nama_kecamatan from kecamatan k,kota ko where k.id_kota = ko.id and ko.id = #{id}")
	@Results(value = {
		@Result (property = "idKecamatan", column = "id"),
		@Result (property = "namaKecamatan", column = "nama_kecamatan")
	})
	List<KelurahanModel> selectKecamatanByIdKota(@Param("id") String idKota);
	
	@Select("SELECT kel.id,kel.nama_kelurahan from kelurahan kel, kecamatan kec "
			+ "where kel.id_kecamatan = kec.id and kec.id = #{id}")
	@Results(value = {
		@Result(property = "id", column = "id"),
		@Result(property = "namaKelurahan", column = "nama_kelurahan"),
	})
	List<KelurahanModel> selectKelurahanByIdKecamatan(@Param("id") String idKecamatan);
}
