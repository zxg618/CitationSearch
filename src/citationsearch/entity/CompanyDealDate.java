package citationsearch.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CompanyDealDate extends Entity
{
    public static final String TABLE = "dbo.unsw_bs_company_deal_date";
    public final String DATE_FORMAT = "dd-MMM-yyyy";

    protected int companyId;

    protected int sourceCompanyId;
    protected Date dealDate;

    public CompanyDealDate() {
        this.companyId = 0;
        this.dealDate = null;
    }

    public int getSourceCompanyId() {
        return sourceCompanyId;
    }

    public void setSourceCompanyId(int sourceCompanyId) {
        this.sourceCompanyId = sourceCompanyId;
    }


    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public void setDealDateFromString(String date)
    {
        try {
            DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            java.util.Date d = formatter.parse(date);
            this.dealDate = new Date(d.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getCompanyIdForSQL()
    {
        if (this.companyId == 0) {
            return "null";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(this.companyId);
            return sb.toString();
        }
    }
}

