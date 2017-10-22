package com.akbar.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.akbar.tugas1.model.KeluargaModel;
import com.akbar.tugas1.model.PendudukModel;
import com.akbar.tugas1.service.KeluargaService;
import com.akbar.tugas1.service.KelurahanService;
import com.akbar.tugas1.service.PendudukService;

@Controller
public class PageController {
	@Autowired
	PendudukService pendudukDAO;
	
	@Autowired
	KeluargaService keluargaDAO;
	
	@Autowired
	KelurahanService kelurahanDAO;
	
	@RequestMapping("/")
	public String home()
	{
		return "form-pendudukkeluarga";
	}
	
	@RequestMapping("/penduduk")
	public String findPenduduk(Model model,@ModelAttribute("sukses_ubah_wafat") String statusWafat,@RequestParam(value = "nik") String nik)
	{
		if(statusWafat.equals("")) {
			PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
			model.addAttribute("penduduk",penduduk);
			return "get-penduduk";
		} else 
		{
			model.addAttribute("nik", nik);
			return "success-updatewafat";
		}	
	}
	
	@PostMapping("/penduduk/mati")
	public RedirectView submitUpdatePendudukMati(Model model, @RequestParam("nik") String nik, RedirectAttributes redirectAtt) {
		pendudukDAO.updateWafat(nik);
		redirectAtt.addFlashAttribute("sukses_ubah_wafat","1");
		redirectAtt.addAttribute("nik",nik);
		return new RedirectView("/penduduk");
	}
	
	@RequestMapping("/keluarga")
	public String findKeluarga(Model model, @RequestParam(value = "nkk") String nkk)
	{
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
		model.addAttribute("keluarga",keluarga);
		return "get-keluarga";
	}
	
	@RequestMapping("/penduduk/tambah")
	public String tambahPenduduk(Model model)
	{
		PendudukModel penduduk = new PendudukModel();
		model.addAttribute("penduduk",penduduk);
		return "form-addpenduduk";
	}
	
	@PostMapping("/penduduk/tambah")
	public String submitTambahPenduduk(Model model, @ModelAttribute PendudukModel penduduk)
	{
		pendudukDAO.insertPenduduk(penduduk);
		model.addAttribute("penduduk",penduduk);
		return "success-addpenduduk";
	}
	
	@RequestMapping("/keluarga/tambah")
	public String tambahKeluarga(Model model) {
		KeluargaModel keluarga = new KeluargaModel();
		model.addAttribute("keluarga",keluarga);
		return "form-addkeluarga";
	}
	
	@PostMapping("/keluarga/tambah")
	public String submitTambahKeluarga(Model model, @ModelAttribute KeluargaModel keluarga)
	{
		keluargaDAO.insertKeluarga(keluarga);
		model.addAttribute("keluarga",keluarga);
		return "success-addkeluarga";
	}
	
	@RequestMapping("/penduduk/ubah/{nomor_ik}")
	public String updatePenduduk(Model model,
			@PathVariable(value = "nomor_ik") String nik) {
		PendudukModel penduduk = pendudukDAO.selectPendudukTable(nik);
		model.addAttribute("penduduk",penduduk);
		return "form-updatependuduk";
	}
	
	@PostMapping("/penduduk/ubah/{nomor_ik}")
	public String submitUpdatePenduduk(Model model, @ModelAttribute PendudukModel penduduk, @PathVariable(value="nomor_ik") String nik)
	{
		pendudukDAO.updatePenduduk(penduduk, nik);
		penduduk.setNik(nik);
		model.addAttribute("penduduk",penduduk);
		return "success-updatependuduk";
	}
	
	@RequestMapping("/keluarga/ubah/{nkk}")
	public String updateKeluarga(Model model,
			@PathVariable(value = "nkk") String nkk)
	{
		KeluargaModel keluarga = keluargaDAO.selectKeluargaTable(nkk);
		model.addAttribute("keluarga",keluarga);
		return "form-updatekeluarga";
	}
	
	@PostMapping("/keluarga/ubah/{nkk}")
	public String submitUpdateKeluarga(Model model, @ModelAttribute KeluargaModel keluarga,
			@PathVariable(value="nkk") String nkk)
	{
		keluargaDAO.updateKeluarga(keluarga, nkk);
		keluarga.setNkk(nkk);
		model.addAttribute("keluarga",keluarga);
		return "success-updatekeluarga";
	}
		
	@GetMapping("/penduduk/cari")
	public String viewPenduduk(Model model,@RequestParam(value="kt",required=false) String idKota,
			@RequestParam(value = "kc",required =false) String idKecamatan, 
			@RequestParam(value="kl",required=false) String idKelurahan)
	{
		if(idKota == null) {
			model.addAttribute("listKota",kelurahanDAO.selectAllKota());
			return "form-viewpenduduk";
		} else if(idKecamatan == null) {
			model.addAttribute("listKecamatan",kelurahanDAO.selectKecamatanByIdKota(idKota));
			model.addAttribute("namaKota",kelurahanDAO.selectKotaById(idKota));
			model.addAttribute("idKota",idKota);
			return "form-viewpenduduk";
		} else if(idKelurahan == null) {
			model.addAttribute("listKelurahan",kelurahanDAO.selectKelurahanByIdKecamatan(idKecamatan));
			model.addAttribute("namaKota",kelurahanDAO.selectKotaById(idKota));
			model.addAttribute("namaKecamatan",kelurahanDAO.selectKecamatanById(idKecamatan));
			model.addAttribute("idKota",idKota);
			model.addAttribute("idKecamatan",idKecamatan);
			return "form-viewpenduduk";
		} else {
			List<PendudukModel> list = pendudukDAO.selectPendudukByIdKelurahan(idKelurahan);
			model.addAttribute("listPenduduk", list);
			model.addAttribute("pendudukMuda", pendudukDAO.orangTermuda(list));
			model.addAttribute("pendudukTua", pendudukDAO.orangTertua(list));
			model.addAttribute("namaKota",kelurahanDAO.selectKotaById(idKota));
			model.addAttribute("namaKecamatan",kelurahanDAO.selectKecamatanById(idKecamatan));
			model.addAttribute("namaKelurahan", kelurahanDAO.selectKelurahanById(idKelurahan));
			model.addAttribute("idKota",idKota);
			model.addAttribute("idKecamatan",idKecamatan);
			model.addAttribute("idKelurahan",idKelurahan);
			return "view-penduduk";
		}
	}
}