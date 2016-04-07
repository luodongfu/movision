package com.zhuhuibao.mybatis.memCenter.service;

import com.zhuhuibao.common.AccountBean;
import com.zhuhuibao.common.AskPriceResultBean;
import com.zhuhuibao.common.JsonResult;
import com.zhuhuibao.common.ResultBean;
import com.zhuhuibao.mybatis.memCenter.entity.*;
import com.zhuhuibao.mybatis.memCenter.mapper.*;
import com.zhuhuibao.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员中心业务处理
 * @author cxx
 *
 */
@Service
@Transactional
public class MemberService {
	private static final Logger log = LoggerFactory.getLogger(MemberService.class);

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private CertificateMapper certificateMapper;

	@Autowired
	private WorkTypeMapper workTypeMapper;

	@Autowired
	private EnterpriseTypeMapper enterpriseTypeMapper;

	@Autowired
	private IdentityMapper identityMapper;

	@Autowired
	private ProvinceMapper provinceMapper;

	@Autowired
	private CityMapper cityMapper;

	@Autowired
	private AreaMapper areaMapper;

	@Autowired
	private CertificateRecordMapper certificateRecordMapper;

	@Autowired
	private EmployeeSizeMapper employeeSizeMapper;
	/**
	 * 会员信息保存
	 */
	public int updateMemInfo(Member member)
	{
		log.debug("会员信息保存");
		int result = 0;
		result = memberMapper.updateMemInfo(member);
		return result;
	}

	/**
	 * 根据会员ID查询会员信息
	 */
	public Member findMemById(String id)
	{
		log.debug("根据会员ID查询会员信息");
		Member member = memberMapper.findMemById(id);
		return member;
	}

	/**
	 * 新建员工
	 */
	public int addMember(Member member)
	{
		log.debug("新建员工");
		int result = 0;
		result = memberMapper.addMember(member);
		return result;
	}

	/**
	 * 修改员工
	 */
	public int updateMember(Member member)
	{
		log.debug("修改员工");
		int result = 0;
		result = memberMapper.updateMember(member);
		return result;
	}

	/**
	 * 禁用员工
	 */
	/*public int disableMember(Member member)
	{
		log.debug("修改员工");
		int result = 0;
		result = memberMapper.disableMember(member);
		return result;
	}*/

	/**
	 * 删除员工
	 */
	public int deleteMember(String id)
	{
		log.debug("修改员工");
		int result = 0;
		result = memberMapper.deleteMember(id);
		return result;
	}

	/**
	 * 员工密码重置
	 */
	public int resetPwd(Member member)
	{
		log.debug("密码重置");
		int result = 0;
		result = memberMapper.resetPwd(member);
		return result;
	}

	/**
	 * 会员头像修改
	 */
	public int uploadHeadShot(Member member)
	{
		log.debug("会员头像修改");
		int isUpdate = memberMapper.uploadHeadShot(member);
		return isUpdate;
	}

	/**
	 * 公司logo修改
	 */
	public int uploadLogo(Member member)
	{
		log.debug("公司logo修改");
		int isUpdate = memberMapper.uploadLogo(member);
		return isUpdate;
	}

	/**
	 * 查询代理商
	 */
	public List<AccountBean> findAgentMember(String account,String type)
	{
		log.debug("查询代理商");
		List<AccountBean> memList = memberMapper.findAgentMember(account,type);
		return memList;
	}

	/**
	 * 根据会员账号查询会员
	 */
	public Member findMem(Member member)
	{
		log.debug("根据会员账号查询会员");
		Member mem = memberMapper.findMem(member);
		return mem;
	}

	/**
	 * 资质类型
	 */
	public List<Certificate> findCertificateList(String type)
	{
		log.debug("资质类型");
		List<Certificate> certificate = certificateMapper.findCertificateList(type);
		return certificate;
	}

	/**
	 * 工作类别
	 */
	public List<WorkType> findWorkTypeList()
	{
		log.debug("工作类别");
		List<WorkType> workType = workTypeMapper.findWorkTypeList();
		return workType;
	}

	/**
	 * 根据id查询工作类别
	 */
	public WorkType findWorkTypeById(String id)
	{
		log.debug("根据id查询工作类别");
		WorkType workType = workTypeMapper.findWorkTypeById(id);
		return workType;
	}

	/**
	 * 人员规模
	 */
	public List<EmployeeSize> findEmployeeSizeList()
	{
		log.debug("人员规模");
		List<EmployeeSize> employeeSize = employeeSizeMapper.findEmployeeSizeList();
		return employeeSize;
	}

	/**
	 * 企业性质
	 */
	public List<EnterpriseType> findEnterpriseTypeList()
	{
		log.debug("企业性质");
		List<EnterpriseType> enterpriseType = enterpriseTypeMapper.findEnterpriseTypeList();
		return enterpriseType;
	}

	/**
	 * 企业身份
	 */
	public List<Identity> findIdentityList()
	{
		log.debug("企业身份");
		List<Identity> identity = identityMapper.findIdentityList();
		return identity;
	}

	/**
	 * 查询省
	 */
	public List<ResultBean> findProvince()
	{
		log.debug("查询省");
		List<ResultBean> province = provinceMapper.findProvince();
		return province;
	}

	/**
	 * 根据省Code查询市
	 */
	public List<ResultBean> findCity(String provincecode)
	{
		log.debug("根据省Code查询市");
		List<ResultBean> city = cityMapper.findCity(provincecode);
		return city;
	}

	/**
	 * 根据市Code查询县区
	 */
	public List<ResultBean> findArea(String cityCode)
	{
		log.debug("根据市Code查询县区");
		List<ResultBean> area = areaMapper.findArea(cityCode);
		return area;
	}

	/**
	 * 资质保存
	 */
	public int saveCertificate(CertificateRecord record)
	{
		log.debug("资质保存");
		int isSave = certificateRecordMapper.saveCertificate(record);
		return isSave;
	}

	/**
	 * 资质编辑
	 */
	public int updateCertificate(CertificateRecord record)
	{
		log.debug("资质编辑");
		int isUpdate = certificateRecordMapper.updateCertificate(record);
		return isUpdate;
	}

	/**
	 * 资质删除
	 */
	public int deleteCertificate(CertificateRecord record)
	{
		log.debug("资质删除");
		int isDelete = certificateRecordMapper.deleteCertificate(record);
		return isDelete;
	}

	/**
	 * 查询资质
	 */
	public List<CertificateRecord> certificateSearch(CertificateRecord record)
	{
		log.debug("查询资质");
		List<CertificateRecord> list = certificateRecordMapper.certificateSearch(record);
		return list;
	}

	/**
	 * 根据父类ID查询公司下属员工
	 */
	public JsonResult findStaffByParentId(Paging<Member> pager, Member member)
	{
		log.debug("根据父类ID查询公司下属员工");
		JsonResult result = new JsonResult();
		List<Member> memberList = memberMapper.findAllByPager(pager.getRowBounds(),member);
		List list = new ArrayList();
		for(int i=0;i<memberList.size();i++){
			Map map = new HashMap();
			Member member1 = memberList.get(i);
			map.put("id",member1.getId());
			if(member1.getMobile()!=null){
				map.put("account",member1.getMobile());
			}else{
				map.put("account",member1.getEmail());
			}

			String workTypeName = "";
			if(member1.getWorkType()!=null){
				WorkType workType = workTypeMapper.findWorkTypeById(member1.getWorkType().toString());
				workTypeName = workType.getName();
			}

			map.put("role",workTypeName);
			map.put("roleId",member1.getWorkType());
			map.put("name",member1.getPersonRealName());
			map.put("status",member1.getStatus());
			String statusName = "";
			if(member1.getStatus()==0){
				statusName = "未激活";
			}else if(member1.getStatus()==1){
				statusName = "正常";
			}else{
				statusName = "注销";
			}
			map.put("statusName",statusName);
			list.add(map);
		}
		pager.result(list);
		result.setCode(200);
		result.setData(pager);
		return result;
	}


}
