package com.oti.srm.controller.srm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oti.srm.dto.ListFilter;
import com.oti.srm.dto.Member;
import com.oti.srm.dto.Pager;
import com.oti.srm.dto.Request;
import com.oti.srm.dto.RequestProcess;
import com.oti.srm.dto.SelectPM;
import com.oti.srm.dto.StatusHistoryFile;
import com.oti.srm.encrypt.AesUtil;
import com.oti.srm.service.member.IMemberService;
import com.oti.srm.service.member.IUserRegisterService;
import com.oti.srm.service.srm.ICommonService;
import com.oti.srm.service.srm.IPMService;
import com.oti.srm.service.srm.IRequestRegisterService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/customer")
public class RequestController {

	@Autowired
	private IUserRegisterService userRegisterService;
	@Autowired
	private IRequestRegisterService requestService;
	@Autowired
	private ICommonService commonService;
	@Autowired
	private IPMService pMService;
	@Autowired
	private IMemberService memberService;
	
	/** 작성자 : 강지성
	 *  유저 등록 페이지 조회
	 * @param model : return 정보 저장
	 * @param session 로그인 사용자 정보
	 * @return 유저 등록 JSP
	 */
	@GetMapping("/register")
	public String register(Model model, HttpSession session) {
		List<System> systemList = userRegisterService.getSystemList();
		model.addAttribute("systemList", systemList);
		
		//사이드바 버튼 저장
		session.setAttribute("where", "register");
		
		return "member/userregister_re";
	}

	/**작성자 : 강지성
	 * 유저 등록 기능
	 * @param member 작성자 정보
	 * @param model : return 정보 저장
	 * @return 유저 등록 페이지 redirect
	 */
	@PostMapping("/register")
	public String register(Member member, Model model) {
		String address = member.getPostcode() + ":" + member.getAddr1() + ":" + member.getAddr2();
		member.setAddress(address);
		member.setPassword("0000");
		MultipartFile mfile = member.getMfile();
		log.info(address);
		
		try {
			if (mfile != null && !mfile.isEmpty()) {
				member.setFileName(mfile.getOriginalFilename());
				member.setSavedDate(new Date());
				member.setFileType(mfile.getContentType());
				member.setFileData(mfile.getBytes());

				int result = userRegisterService.register(member);
				if (result == IUserRegisterService.REGISTER_FAIL) {
					return "redirect:/customer/register";
				} else {
					result = IUserRegisterService.REGISTER_SUCCESS;
					return "redirect:/customer/register";
				}
			} else {
				int result = userRegisterService.register(member);
				return "redirect:/customer/register";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("registerResult", "FAIL");
			return "redirect:/customer/register";
		}
	}

	/**작성자 : 강지성
	 * 내 정보 조회 기능 
	 * @param session : 사용자 정보 조회
	 * @param model : return 정보 저장
	 * @return mypage JSP 
	 */
	@GetMapping("/mypage")
	public String myPage(HttpSession session, Model model) {
		// 내 정보 조회
		Member Sessionmember = (Member) session.getAttribute("member");
		Member returnMember = userRegisterService.getUserInfo(Sessionmember.getMid());

		// 주소 변환
		String[] address = returnMember.getAddress().split(":");
		returnMember.setPostcode(Integer.parseInt(address[0]));
		returnMember.setAddr1(address[1]);
		returnMember.setAddr2(address[2]);
		
		log.info(Sessionmember.getGender());
		
		//시스템
		List<System> systemList = userRegisterService.getSystemList();
		model.addAttribute("systemList", systemList);
		
		model.addAttribute("returnMember", returnMember);

		return "member/mypage_re";
	}
	
	/**작성자 : 강지성
	 * 내 정보 수정 기능
	 * @param member : 사용자 정보
	 * @param model : return 정보 저장
	 * @param session : 수정된 사용자 정보 저장 
	 * @return home JSP redirect
	 */
	@PostMapping("/mypageupdate")
	public String myPageUpdate(Member member, Model model, HttpSession session) {
		Member Sessionmember = (Member) session.getAttribute("member");
		log.info(Sessionmember);
		String address = member.getPostcode() + ":" + member.getAddr1() + ":" + member.getAddr2();
		member.setAddress(address);
		member.setSno(Sessionmember.getSno());
		MultipartFile mfile = member.getMfile();
		
		try {
			
			if (mfile != null && !mfile.isEmpty()) {
				member.setFileName(mfile.getOriginalFilename());
				member.setSavedDate(new Date());
				member.setFileType(mfile.getContentType());
				member.setFileData(mfile.getBytes());
				//유저 정보 수정
				int result = userRegisterService.updateUserInfo(member);
				log.info(Sessionmember.getMtype());
				Member newMember = memberService.getMember(member);
				session.setAttribute("member", newMember);
				
				if(Sessionmember.getMtype().equals("pm")){
					return "redirect:/pmhome";
				} else if(Sessionmember.getMtype().equals("user")){
					return "redirect:/userhome";
				} else {
					return "redirect:/";
				}
				
			}
		} catch(Exception e) {
			
		}
			int result = userRegisterService.updateUserInfo(member);
			log.info(Sessionmember.getMtype());
			Member newMember = memberService.getMember(member);
			session.setAttribute("member", newMember);
			
			if(Sessionmember.getMtype().equals("pm")){
				return "redirect:/pmhome";
			} else if(Sessionmember.getMtype().equals("user")){
				return "redirect:/userhome";
			} else {
				return "redirect:/";
			}
	}
	

	/**작성자 : 강지성
	 * 내 정보 이미지 출력 메소드
	 * @param mid : 이미지 찾기 위한 정보
	 * @return 이미지 파일
	 */
	@GetMapping("/mypage/{mid}")
	public ResponseEntity<byte[]> returnImg(@PathVariable String mid) {
		
		Member returnMember = userRegisterService.getUserInfo(mid);
		
		if(returnMember.getFileName() == null) {
			returnMember = userRegisterService.getUserInfo("img");
		}
		HttpHeaders headers = new HttpHeaders();
		String[] fileTypes = returnMember.getFileType().split("/");
		headers.setContentType(new MediaType(fileTypes[0], fileTypes[1]));
		headers.setContentDispositionFormData("attachment", returnMember.getFileName());
		return new ResponseEntity<byte[]>(returnMember.getFileData(), headers, HttpStatus.OK);

		
	}

	/**작성자 : 강지성
	 * 요청 작성 페이지 조회
	 * @param member : 사용자 정보
	 * @param request : 요청 작성시 기본 상태값 저장, 전달
	 * @param model : return 정보 저장
	 * @param requestProcess : 요청 작성시 초기 단계값 저장
	 * @param session : 사용자 정보 return
	 * @return 요청 작성 페이지
	 */
	@GetMapping("/request")
	public String customerRequest(Member member, Request request, Model model, RequestProcess requestProcess,
			HttpSession session) {
		// 로그인 member 정보는 JSP에서 SessionScope 이용하여 표시
		// 요청 단계 (default 값으로 지정하여 전달)
		request.setStatusName("접수중");
		request.setStatusNo(1);
		requestProcess.setReqType("정규");
		//세션에 저장된 멤버 객체 전달, 번호 복호화
		Member sessionMember = (Member) session.getAttribute("member");
		log.info(sessionMember.toString());
		if(sessionMember.getPhone().charAt(0) != '0') {
			sessionMember.setPhone(AesUtil.decrypt(sessionMember.getPhone()));
		}
		model.addAttribute("returnMember", sessionMember);
		
		// 시스템 리스트 전달
		List<System> systemList = userRegisterService.getSystemList();
		model.addAttribute("request", request);
		model.addAttribute("requestProcess", requestProcess);
		model.addAttribute("systemList", systemList);
		model.addAttribute("member", sessionMember);
		return "srm/request/request";
	}
	
	/**작성자 : 강지성
	 * 요청 작성 등록 기능
	 * @param request : 사용자가 작성한 요청 정보
	 * @param model : return 정보 저장
	 * @param session : 작성자 정보
	 * @param files : 첨부된 파일 정보
	 * @return 권한별 list JSP redirect
	 */
	@PostMapping("/request")
	public String customerRequest(Request request, Model model, HttpSession session,
			@RequestParam("mfile[]") MultipartFile[] files) {
		// 요청 상태값은 1
		request.setStatusNo(1);
		Member member = (Member) session.getAttribute("member");
		request.setClient(member.getMid());

		List<StatusHistoryFile> fileList = new ArrayList<>();

		try {
			if (files != null) {
				for (MultipartFile file : files) {
					if (!file.isEmpty()) {
						StatusHistoryFile shf = new StatusHistoryFile();
						shf.setFileData(file.getBytes());
						shf.setFileName(file.getOriginalFilename());
						shf.setFileType(file.getContentType());
						fileList.add(shf);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int result = requestService.writeRequest(request, fileList);
		if (result == IRequestRegisterService.REQUEST_SUCCESS) {
			if(member.getMtype().equals("user")) {
				return "redirect:/customer/userrequestlist";
			} else {
				return "redirect:/customer/requestlist";
			}
		} else {
			model.addAttribute("requestResult", "FAIL");
			if(member.getMtype().equals("user")) {
				return "redirect:/customer/userrequestlist";
			} else {
				return "redirect:/customer/requestlist";
			}
		}
	}
	
	//(유저) 요청 리스트 초기 조회

	/**작성자 : 강지성
	 * (User) 요청 목록 초기 조회, 출력
	 * @param model : JSP에 담을 정보 전달
	 * @param session : 사이드바 정보 저장
	 * @return
	 */
	@GetMapping("/userrequestlist")
	public String myrequestlist (Model model, HttpSession session) {
		//시스템
		List<System> systemList = userRegisterService.getSystemList();
		model.addAttribute("systemList", systemList);
		
		// 유저 정보 전달
		Member member = (Member) session.getAttribute("member");
		
		//초기값
		ListFilter listFilter = new ListFilter();
		listFilter.setReqType("전체");
		listFilter.setDateFirst("");
		listFilter.setDateLast("");
		listFilter.setSno(0);
		listFilter.setStatusNo(0);
		listFilter.setPageNo(1);
		
		ListFilter returnList = requestService.dateFilterList(listFilter);
		// 보여줄 행 수 조회
		
		int totalRows = requestService.getRequestListRows(listFilter, member);
		Pager pager = new Pager(7, 5, totalRows, listFilter.getPageNo());
		List<SelectPM> requestList = requestService.getMyRequestList(listFilter, pager, member);
		
		// 시스템 리스트 전달
		// 목록 리스트와 페이지 return
		model.addAttribute("requestList", requestList);
		model.addAttribute("pager", pager);
		// filter 전달
		model.addAttribute("listFilter", returnList);
		
		//사이드바 버튼 저장
		session.setAttribute("where", "list");
		
		return "srm/uesrrequestlist";
	}
	
	/**작성자 : 강지성
	 * (User) 요청 목록 검색, 페이지 이동 조회 (AJAX)
	 * @param listFilter : 사용자가 검색한 정보 저장
	 * @param model : return 시 JSP 출력할 정보
	 * @param session : 사용자 정보
	 * @return
	 */
	@PostMapping("/myrequestlist")
	public String myRequestList(@RequestBody ListFilter listFilter, Model model, HttpSession session) {
		// 요청 조회 필터
		List<System> systemList = userRegisterService.getSystemList();
		// 유저 정보 전달
		Member member = (Member) session.getAttribute("member");
		ListFilter returnList = requestService.dateFilterList(listFilter);
		// 보여줄 행 수 조회
		int totalRows = requestService.getRequestListRows(listFilter, member);
		if(listFilter.getPageNo()==0) {
			listFilter.setPageNo(1);
		}
		Pager pager = new Pager(7, 5, totalRows, listFilter.getPageNo());
		List<SelectPM> requestList = requestService.getMyRequestList(listFilter, pager, member);
		
		// 시스템 리스트 전달
		model.addAttribute("systemList", systemList);
		// 목록 리스트와 페이지 return
		model.addAttribute("requestList", requestList);
		model.addAttribute("pager", pager);
		// filter 전달
		model.addAttribute("listFilter", returnList);
		return "srm/list/ajaxmyrequestlist";
	}
	
	//(담당자) 요청 목록 조회 ajax
		/**작성자 : 강지성
		 * (담당자) 요청 목록 검색, 페이지 이동 조회 (AJAX)
		 * @param listFilter : 사용자가 검색한 정보 저장
		 * @param model : return 시 JSP 출력할 정보
		 * @param session : 사용자 정보
		 * @return
		 */
		@PostMapping("/workerrequestlist")
		public String workerRequestList(@RequestBody ListFilter listFilter, Model model, HttpSession session) {
			// 요청 조회 필터
			List<System> systemList = userRegisterService.getSystemList();
			// 유저 정보 전달
			Member member = (Member) session.getAttribute("member");
			ListFilter returnList = requestService.dateFilterList(listFilter);
			// 보여줄 행 수 조회
			int totalRows = requestService.getWorkerRequestListRows(listFilter, member);
			if(listFilter.getPageNo()==0) {
				listFilter.setPageNo(1);
			}
			Pager pager = new Pager(7, 5, totalRows, listFilter.getPageNo());
			List<SelectPM> requestList = requestService.getWorkerRequestList(listFilter, pager, member);
			
			
			// 시스템 리스트 전달
			model.addAttribute("systemList", systemList);
			// 목록 리스트와 페이지 return
			model.addAttribute("requestList", requestList);
			model.addAttribute("pager", pager);
			// filter 전달
			model.addAttribute("listFilter", returnList);
			return "srm/list/ajaxmyrequestlist";
		}
		
		
	/**작성자 : 강지성
	 * (담당자) 업무 목록 검색, 페이지 이동 조회
	 * @param statusNo : 초기 조회시 전체 검색을 위한 상태값 지정
	 * @param model : return 시 JSP 출력할 정보
	 * @param session : 사용자 정보
	 * @return
	 */
	@GetMapping("/requestlist")
	public String requestList(@RequestParam(defaultValue="0") int statusNo, Model model, HttpSession session) {
		List<System> systemList = userRegisterService.getSystemList();
		// 유저 정보 전달
		Member member = (Member) session.getAttribute("member");
		//초기값
		ListFilter listFilter = new ListFilter();
		listFilter.setReqType("전체");
		listFilter.setDateFirst("");
		listFilter.setDateLast("");
		listFilter.setSno(0);
		listFilter.setStatusNo(statusNo);
		log.info("전달받은 상태값" + statusNo);
		listFilter.setPageNo(1);
		ListFilter returnList = requestService.dateFilterList(listFilter);
		int totalRows = requestService.getMyWorkRows(listFilter, member);
		Pager pager = new Pager(7, 5, totalRows, listFilter.getPageNo());
		List<SelectPM> requestList = requestService.getMyWorkList(listFilter, pager, member);
		// 시스템 리스트 전달
		model.addAttribute("systemList", systemList);
		// 목록 리스트와 페이지 return
		model.addAttribute("requestList", requestList);
		model.addAttribute("pager", pager);
		// filter 전달
		model.addAttribute("listFilter", returnList);
		
		//사이드바 버튼 저장
		session.setAttribute("where", "list");
		
		return "srm/requestlist_re";
	}
	//담당 요청 목록 조회 ajax
	/** (담당자) 업무 목록 검색, 페이지 이동 조회 (AJAX)
	 * @param listFilter : 사용자가 지정한 검색 조건
	 * @param model : JSP에 표시할 정보 
	 * @param session : 사용자 정보
	 * @return
	 */
	@PostMapping("/myworklist")
	public String myWrokList(@RequestBody ListFilter listFilter, Model model, HttpSession session) {

		// 필터에 출력할 시스템 리스트 조회
		List<System> systemList = userRegisterService.getSystemList();
		ListFilter returnList = requestService.dateFilterList(listFilter);
		
		if(listFilter.getColumnName() == "" || listFilter.getSortState() == "") {
			listFilter.setColumnName(null);
			listFilter.setSortState(null);
		}
		
		//세션에 저장된 멤버 객체 전달
		Member member = (Member) session.getAttribute("member");
		int totalRows = requestService.getMyWorkRows(listFilter, member);
		
		if(listFilter.getPageNo()==0) {
			listFilter.setPageNo(1);
		}
		
		Pager pager = new Pager(7, 5, totalRows, listFilter.getPageNo());
		List<SelectPM> requestList = requestService.getMyWorkList(listFilter, pager, member);
		
		log.info(pager.getTotalRows());
		
		log.info(listFilter.toString());
		// 시스템 리스트 전달
		model.addAttribute("systemList", systemList);
		// 목록 리스트와 페이지 return
		model.addAttribute("requestList", requestList);
		model.addAttribute("pager", pager);
		log.info(pager);
		// filter 전달, 정렬 상태 전달
		model.addAttribute("listFilter", returnList);
		
		
		return "srm/list/ajaxmyworklist";
	}
	/**작성자 : 강지성
	 * 목록에 출력할 단계 정보 불러오기
	 * @param request : 단계를 가져올 요청 정보 출력
	 * @return : 출력 결과 return
	 */
	@PostMapping("/viewstep")
	@ResponseBody
	public int viewStep(Request request) {
		int result = requestService.getPresentStep(request.getRno());
		return result;
	}
	
	/**작성자 : 강지성
	 * 요청 글 상세 조회
	 * @param rno : 조회할 글 번호
	 * @param session : 사용자 정보
	 * @param model : JSP 표시할 정보 저장
	 * @return : 상세 조회 JSP
	 */
	@GetMapping("/requestdetail")
	@Transactional
	public String userRequestDetail(int rno, HttpSession session, Model model) {
		Request request = requestService.getRequestDetail(rno);
		List<System> systemList = userRegisterService.getSystemList();
		RequestProcess requestProcess = commonService.getRequestProcess(rno);
		if(request.getRphone().charAt(0) != '0') {
			request.setRphone(AesUtil.decrypt(request.getRphone()));
		}
		
		model.addAttribute("request", request);
		model.addAttribute("requestProcess", requestProcess);
		model.addAttribute("systemList", systemList);
		model.addAttribute("testRejectExist", commonService.isThereTestReject(rno));
		
		return "srm/request/requestdetail";
	}
	
	/**작성자 : 강지성
	 * 요청 수정 기능
	 * @param request : 요청 수정 정보 저장
	 * @param session : 요청 수정한 사용자의 정보
	 * @param mulfiles : 수정 요청 시 사용할 file
	 * @return : 해당 요청으로 redirect
	 */
	@PostMapping("/requestupdate")
	public String requestUpdate(Request request, HttpSession session, MultipartFile[] mulfiles) {
		
		Member member = (Member) session.getAttribute("member");
		request.setClient(member.getMid());
		List<StatusHistoryFile> sFiles = new ArrayList<StatusHistoryFile>();

		try {
			if (mulfiles != null) {
				for (MultipartFile file : mulfiles) {
					if (!file.isEmpty()) {
						StatusHistoryFile shf = new StatusHistoryFile();
						shf.setFileData(file.getBytes());
						shf.setFileName(file.getOriginalFilename());
						shf.setFileType(file.getContentType());
						sFiles.add(shf);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		requestService.updateRequest(request, sFiles);
		return "redirect:/customer/requestdetail?rno=" + request.getRno();
	}
	
	/**작성자 : 강지성
	 * 요청 글의 파일 다운로드 기능
	 * @param fno : DB 내 파일이 저장된 게시글 번호
	 * @return : 파일
	 * @throws UnsupportedEncodingException
	 */
	@GetMapping("/requestdetail/filedownload/{fno}")
	public ResponseEntity<byte[]> filDownload(@PathVariable int fno) throws UnsupportedEncodingException {
		
		log.info(fno);
		
		StatusHistoryFile file = requestService.getMyRequestFile(fno);
		final HttpHeaders headers = new HttpHeaders();
		String [] mtypes = file.getFileType().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		headers.setContentDispositionFormData("attachment",new String(file.getFileName().getBytes("UTF-8"), "ISO-8859-1"));
		
		return new ResponseEntity<byte[]>(file.getFileData(), headers, HttpStatus.OK);
	}
	
	/**작성자 : 강지성
	 * 요청 수정시 파일 업로드 AJAX
	 * @param param : 사용자가 수정한 파일 정보
	 * @param files : 사용자가 수정한 파일 정보
	 * @return 실행 결과
	 * @throws IOException
	 */
	@PostMapping("/requestfileupload")
	@ResponseBody
	public int uploadFile(@RequestParam HashMap<Object, Object> param, @RequestParam("files")MultipartFile[] files) throws IOException {
		String rno = (String) param.get("rno");
		log.info(rno);
		List<StatusHistoryFile> fileList = new ArrayList<>();
		if(files != null) {
			for(MultipartFile file : files) {
				if(!file.isEmpty()) {
					StatusHistoryFile shf = new StatusHistoryFile();
					shf.setFileData(file.getBytes());
					shf.setFileName(file.getOriginalFilename());
					shf.setFileType(file.getContentType());
					fileList.add(shf);
				}
			}
		}
		int result = requestService.updateRequestFile(rno, fileList);
		
		
		return 1;
	}
	
	//요청 삭제
		@PostMapping("/requestdelete")
		public String deleteRequest(int rno, HttpSession session) throws IOException {
			log.info("실행");
			requestService.deleteRequest(rno);
			Member member = (Member) session.getAttribute("member");
			if(member.getMtype().equals("user")) {
				return "redirect:/customer/userrequestlist";
			}else {
				return "redirect:/customer/requestlist";
			}
		}
	

	
	
	
	

}
