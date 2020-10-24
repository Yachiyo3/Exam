package com.qf.service;

import com.qf.dao.ContactDAO;
import com.qf.dao.ContactDAOImpl;
import com.qf.domain.Contact;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Resource(name="contactDAO")
    private ContactDAO dao;
    public void setDao(ContactDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Contact> queryAll() {
        return dao.queryAll();
    }

    @Override
    public List<Contact> queryAll(int currentPage, int pageSize) {
        int pageOffset=(currentPage-1)*pageSize;
        return dao.queryAll(pageOffset,pageSize);
    }

    @Override
    public Contact queryById(String contactId) {
        return dao.queryById(contactId);
    }

    @Override
    public boolean update(Contact contact) {
        int result = dao.update(contact);
        return result == 1;//1代表一条，修改是一条一条改，如果恒等于1证明boolean==ture，便修改成功了
    }

    @Override
    public boolean add(Contact contact) {
        int result = dao.add(contact);
        return result == 1;
    }

    @Override
    public int queryPageCount(int pageSize) {
        // 记录条数
        int recordCount = dao.queryCount();

        // 如果一共60条，且一页10条的话，一共几页？
        // 60 / 10 => 6
        // 66 / 10 => 7

        return (int) Math.ceil(recordCount / (double) pageSize);
    }

    @Override
    public boolean deleteById(String contactId) {
        int num=dao.deleteById(contactId);
        return num==1;
    }

}
