package citationsearch.entity;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CitStats extends Entity
{
    public static final String TABLE = "dbo.[unsw_bs_CIT_stats]";
    public final String DATE_FORMAT = "dd-MMM-yyyy";

    protected int companyId;
    protected int sourceCompanyId;
    protected Date dealDate;

    private int CN_CIT_AT_DEAL_ON_PAT_AT_DEAL;
    private int FR_CIT_AT_DEAL_ON_PAT_AT_DEAL;
    private int CN_CIT_AT_2016_ON_PAT_AT_DEAL;
    private int FR_CIT_AT_2016_ON_PAT_AT_DEAL;
    private int CN_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL;
    private int FR_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL;
    private int CN_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL;
    private int FR_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL;

    public int getCompanyId() {
        return companyId;
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

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getSourceCompanyId() {
        return sourceCompanyId;
    }

    public void setSourceCompanyId(int sourceCompanyId) {
        this.sourceCompanyId = sourceCompanyId;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public int getCN_CIT_AT_DEAL_ON_PAT_AT_DEAL() {
        return CN_CIT_AT_DEAL_ON_PAT_AT_DEAL;
    }

    public void setCN_CIT_AT_DEAL_ON_PAT_AT_DEAL(int CN_CIT_AT_DEAL_ON_PAT_AT_DEAL) {
        this.CN_CIT_AT_DEAL_ON_PAT_AT_DEAL = CN_CIT_AT_DEAL_ON_PAT_AT_DEAL;
    }

    public int getFR_CIT_AT_DEAL_ON_PAT_AT_DEAL() {
        return FR_CIT_AT_DEAL_ON_PAT_AT_DEAL;
    }

    public void setFR_CIT_AT_DEAL_ON_PAT_AT_DEAL(int FR_CIT_AT_DEAL_ON_PAT_AT_DEAL) {
        this.FR_CIT_AT_DEAL_ON_PAT_AT_DEAL = FR_CIT_AT_DEAL_ON_PAT_AT_DEAL;
    }

    public int getCN_CIT_AT_2016_ON_PAT_AT_DEAL() {
        return CN_CIT_AT_2016_ON_PAT_AT_DEAL;
    }

    public void setCN_CIT_AT_2016_ON_PAT_AT_DEAL(int CN_CIT_AT_2016_ON_PAT_AT_DEAL) {
        this.CN_CIT_AT_2016_ON_PAT_AT_DEAL = CN_CIT_AT_2016_ON_PAT_AT_DEAL;
    }

    public int getFR_CIT_AT_2016_ON_PAT_AT_DEAL() {
        return FR_CIT_AT_2016_ON_PAT_AT_DEAL;
    }

    public void setFR_CIT_AT_2016_ON_PAT_AT_DEAL(int FR_CIT_AT_2016_ON_PAT_AT_DEAL) {
        this.FR_CIT_AT_2016_ON_PAT_AT_DEAL = FR_CIT_AT_2016_ON_PAT_AT_DEAL;
    }

    public int getCN_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL() {
        return CN_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL;
    }

    public void setCN_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL(int CN_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL) {
        this.CN_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL = CN_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL;
    }

    public int getFR_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL() {
        return FR_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL;
    }

    public void setFR_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL(int FR_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL) {
        this.FR_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL = FR_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL;
    }

    public int getCN_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL() {
        return CN_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL;
    }

    public void setCN_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL(int CN_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL) {
        this.CN_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL = CN_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL;
    }

    public int getFR_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL() {
        return FR_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL;
    }

    public void setFR_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL(int FR_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL) {
        this.FR_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL = FR_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL;
    }
}

