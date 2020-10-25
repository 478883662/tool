package com.zhangb.tool.doctorReimbursement.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.zhangb.tool.common.dao.BaseDao;
import com.zhangb.tool.doctorReimbursement.bo.ReimbDrugBo;
import com.zhangb.tool.doctorReimbursement.bo.ReimbIllnessDrugBo;
import com.zhangb.tool.doctorReimbursement.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.tool.doctorReimbursement.entity.ReimbIllness;
import com.zhangb.tool.doctorReimbursement.entity.ReimbUserInfo;
import com.zhangb.tool.doctorReimbursement.remote.service.ICxnhRemoteService;
import com.zhangb.tool.doctorReimbursement.service.IReimbIllnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by z9104 on 2020/9/26.
 */
@Service
public class ReimbIllnessServiceImpl implements IReimbIllnessService {

    @Autowired
    private ICxnhRemoteService remoteService;

    @Override
    public void syncIllnessFromRecord() throws SQLException, IllegalAccessException {
        //查询所有的病例信息
        String sql = "select distinct illness_no,illness_name from tool_deal_record_t";
        List<ReimbIllness> reimbIllnessList = Db.use().query(sql, ReimbIllness.class);
        //同步到病例表
        for (ReimbIllness reimbIllness :reimbIllnessList){
            ReimbIllness where = new ReimbIllness();
            where.setIllnessNo(reimbIllness.getIllnessNo());
            int count = BaseDao.count(where);
            if (count==0){
                //不存在就插入
                BaseDao.insert(reimbIllness);
            }else{
                //存在就更新，只更新有值的部分
                BaseDao.updateForValue(reimbIllness,where);
            }
        }
    }

    @Override
    public List<ReimbDrugBo>  getDrugByIllness(String ylLocationNo, String bizId) throws Exception {
        String resultStr = remoteService.executeRemote(ReimbRemoteStrategyKeyConstants.REIMB_GET_ILLNESS_DRUG_STRATEGY,ylLocationNo,bizId);
        List<ReimbDrugBo>  bo = parseResult(resultStr);
        return bo;
    }

    @Override
    public ReimbIllness getIllness(String illNessNo) throws SQLException, IllegalAccessException {
        ReimbIllness where  = new ReimbIllness();
        where.setIllnessNo(illNessNo);
        List<ReimbIllness> resultList = BaseDao.select(where,ReimbIllness.class);
        if (CollectionUtil.isEmpty(resultList)){
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public ReimbIllness getRandomIllness(ReimbUserInfo user,String currIllnessNo) throws SQLException, IllegalAccessException {
        //获取用户接下来要报销的病例
        int age = Integer.valueOf(user.getAge());
        List<String> illnessNoList = CollectionUtil.newArrayList();
        if (age>40){
            illnessNoList.add("20129");
            illnessNoList.add("30221");
            illnessNoList.add("3449");
        }else{
            illnessNoList.add("20129");
            illnessNoList.add("30221");
        }
        int index = NumberUtil.generateRandomNumber(0,100,1)[0];
        String illnessNo =illnessNoList.get(index%illnessNoList.size());
        return getIllness(illnessNo);
    }

    /**
     * 解析病与用药关系报文
     * @param resultStr
     * @return
     */
    private List<ReimbDrugBo> parseResult(String resultStr) {
        if (StrUtil.hasBlank(resultStr)){
            return null;
        }
        String[] rows = StrUtil.split(resultStr,"\r\n");
        if (ArrayUtil.hasEmpty(rows)){
            return null;
        }
        List<ReimbDrugBo> reimbDrugBoList = CollectionUtil.newArrayList();
        for (String row : rows){
            ReimbDrugBo reimbDrugBo = new ReimbDrugBo();
            String[] cols = StrUtil.split(row,"\t");
            reimbDrugBo.setDrugName(cols[7]);
            reimbDrugBo.setDrugNo(cols[6]);
            reimbDrugBo.setPrice(cols[13]);
            reimbDrugBo.setDrugNum(cols[14]);
            reimbDrugBo.setDrugSeq(cols[12]);
            reimbDrugBo.setOneType(cols[11]);
            reimbDrugBoList.add(reimbDrugBo);
        }
        return reimbDrugBoList;
    }
}
