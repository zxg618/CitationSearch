package citationsearch.mapper;

import citationsearch.entity.Company;
import citationsearch.entity.CompanyDealDate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDealDateMapper extends Mapper
{
    public int save(CompanyDealDate model)
    {
        this.query = "INSERT INTO " + CompanyDealDate.TABLE + " (company_id, source_company_id, deal_date)"
                + " VALUES ("
                + model.getCompanyIdForSQL()
                + ", "
                + model.getSourceCompanyId()
                + ", \'"
                + model.getDealDate()
                + "\')";
        int result = this.executeOtherQuery();

        return result;
    }

    public List<CompanyDealDate> getAllCompanyDealDates() {
        List<CompanyDealDate> companyDealDates = new ArrayList<>();

        this.query = "select * from " + CompanyDealDate.TABLE;
        ResultSet rs = this.executeGetQuery();

        try {
            while (rs.next()) {
                CompanyDealDate cdd = new CompanyDealDate();
                cdd.setCompanyId(rs.getInt("company_id"));
                cdd.setSourceCompanyId(rs.getInt("source_company_id"));
                cdd.setDealDate(rs.getDate("deal_date"));
                companyDealDates.add(cdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companyDealDates;
    }
}
