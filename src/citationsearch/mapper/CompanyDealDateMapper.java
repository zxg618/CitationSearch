package citationsearch.mapper;

import citationsearch.entity.CompanyDealDate;

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
}
