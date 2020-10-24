package com.qf.dao;

import com.qf.domain.Contact;
import com.qf.utils.DataSourceUtils;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component("contactDAO")
public class ContactDAOImpl implements ContactDAO{
   @Autowired
   private JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> queryAll() {
        List<Contact> result=null;
        try {
            String sql="select * from contact_info where del=0";
            result=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Contact.class));
        } catch (Exception e) {
           e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Contact> queryAll(int pageOffset, int pageSize) {
        List<Contact> result=null;
        try {
            String sql="select*from contact_info where del=0 LIMIT ?,? ";
            result=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Contact.class),pageOffset,pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Contact queryById(String contactId) {
        String sql="select * from contact_info where id=? and del=0";
        // query是查多个，返回List，推荐使用
        // queryForObject是查1个，返回javabean。如果通过sql语句，一条都查不到的话，那么会抛异常
        List<Contact> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Contact.class), contactId);

        if (query.size()==1){
            return query.get(0);
        }else {
            return null;
        }
    }

    @Override
    public int queryCount() {
        int result = 0;
        try {
            String sql = "SELECT COUNT(*) FROM contact_info where del=0";

            result =jdbcTemplate.queryForObject(sql,Integer.class);

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    @Override
    public int deleteById(String contactId) {
        int result=0;
        try {
            String sql="update contact_info set del=1 where id=?";
            result=jdbcTemplate.update(sql,contactId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(Contact contact) {
        String sql="update contact_info set name=?,gender=?,birthday=?,birthplace=?,mobile=?,email=? where id=?";
        return jdbcTemplate.update(sql,
                contact.getName(),
                contact.getGender(),
                contact.getBirthday(),
                contact.getBirthplace(),
                contact.getMobile(),
                contact.getEmail(),
                contact.getId());
    }

    @Override
    public int add(Contact contact) {
        String sql="insert into contact_info(`name`, `gender`, `birthday`, `birthplace`, `mobile`, `email`) values(?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                contact.getName(),
                contact.getGender(),
                contact.getBirthday(),
                contact.getBirthplace(),
                contact.getMobile(),
                contact.getEmail());

    }

}
