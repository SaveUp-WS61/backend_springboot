package com.upc.saveup.service.impl;

import com.upc.saveup.model.Company;
import com.upc.saveup.repository.CompanyRepository;
import com.upc.saveup.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CompanyServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void updateCompany(Company company){
        companyRepository.save(company);
    }

    @Override
    public Company getCompany(int id){
        return companyRepository.findById(id).get();
    }

    @Override
    public void deleteCompany(int id){
        companyRepository.deleteById(id);
    }

    @Override
    public List<Company> getAllCompanies(){
        return (List<Company>) companyRepository.findAll();
    }

    @Override
    public boolean isCompanyExist(int id){
        return companyRepository.existsById(id);
    }

    @Override
    public List<Map<String, Object>> getSaleData(int companyId) {
        String query = "SELECT cu.name, cu.last_name, o.id as orders, pr.name as producto, pr.price, ca.quantity, o.date, co.id as company_id " +
                "FROM railway.company co " +
                "INNER JOIN railway.product pr ON co.id = pr.company_id " +
                "INNER JOIN railway.cart ca ON pr.id = ca.product_id " +
                "INNER JOIN railway.orders o ON ca.order_id = o.id " +
                "INNER JOIN railway.pay pa ON o.pay_id = pa.id " +
                "INNER JOIN railway.customer cu ON pa.customer_id = cu.id " +
                "WHERE co.id = ? " +
                "ORDER BY orders ASC;";
        return jdbcTemplate.queryForList(query, companyId);
    }
}
