package citationsearch.entity;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PatStats extends Entity
{
    public static final String TABLE = "dbo.[unsw_bs_PAT_stats]";
    public final String DATE_FORMAT = "dd-MMM-yyyy";

    protected int companyId;
    protected int sourceCompanyId;
    protected Date dealDate;

    int CN_PAT_AT_DEAL_ALL;
    int CN_PAT_AT_DEAL_U;
    int CN_PAT_AT_DEAL_I;
    int CN_PAT_AT_DEAL_A;

    int CN_PAT_3Y_AFTER_DEAL_ALL;
    int CN_PAT_3Y_AFTER_DEAL_U;
    int CN_PAT_3Y_AFTER_DEAL_I;
    int CN_PAT_3Y_AFTER_DEAL_A;

    int CN_PAT_5Y_AFTER_DEAL_ALL;
    int CN_PAT_5Y_AFTER_DEAL_U;
    int CN_PAT_5Y_AFTER_DEAL_I;
    int CN_PAT_5Y_AFTER_DEAL_A;

    int FR_PAT_AT_DEAL_ALL;
    int FR_PAT_AT_DEAL_U;
    int FR_PAT_AT_DEAL_I;
    int FR_PAT_AT_DEAL_A;

    int FR_PAT_3Y_AFTER_DEAL_ALL;
    int FR_PAT_3Y_AFTER_DEAL_U;
    int FR_PAT_3Y_AFTER_DEAL_I;
    int FR_PAT_3Y_AFTER_DEAL_A;

    int FR_PAT_5Y_AFTER_DEAL_ALL;
    int FR_PAT_5Y_AFTER_DEAL_U;
    int FR_PAT_5Y_AFTER_DEAL_I;
    int FR_PAT_5Y_AFTER_DEAL_A;

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

    public int getCN_PAT_AT_DEAL_ALL() {
        return CN_PAT_AT_DEAL_ALL;
    }

    public void setCN_PAT_AT_DEAL_ALL(int CN_PAT_AT_DEAL_ALL) {
        this.CN_PAT_AT_DEAL_ALL = CN_PAT_AT_DEAL_ALL;
    }

    public int getCN_PAT_AT_DEAL_U() {
        return CN_PAT_AT_DEAL_U;
    }

    public void setCN_PAT_AT_DEAL_U(int CN_PAT_AT_DEAL_U) {
        this.CN_PAT_AT_DEAL_U = CN_PAT_AT_DEAL_U;
    }

    public int getCN_PAT_AT_DEAL_I() {
        return CN_PAT_AT_DEAL_I;
    }

    public void setCN_PAT_AT_DEAL_I(int CN_PAT_AT_DEAL_I) {
        this.CN_PAT_AT_DEAL_I = CN_PAT_AT_DEAL_I;
    }

    public int getCN_PAT_AT_DEAL_A() {
        return CN_PAT_AT_DEAL_A;
    }

    public void setCN_PAT_AT_DEAL_A(int CN_PAT_AT_DEAL_A) {
        this.CN_PAT_AT_DEAL_A = CN_PAT_AT_DEAL_A;
    }

    public int getCN_PAT_3Y_AFTER_DEAL_ALL() {
        return CN_PAT_3Y_AFTER_DEAL_ALL;
    }

    public void setCN_PAT_3Y_AFTER_DEAL_ALL(int CN_PAT_3Y_AFTER_DEAL_ALL) {
        this.CN_PAT_3Y_AFTER_DEAL_ALL = CN_PAT_3Y_AFTER_DEAL_ALL;
    }

    public int getCN_PAT_3Y_AFTER_DEAL_U() {
        return CN_PAT_3Y_AFTER_DEAL_U;
    }

    public void setCN_PAT_3Y_AFTER_DEAL_U(int CN_PAT_3Y_AFTER_DEAL_U) {
        this.CN_PAT_3Y_AFTER_DEAL_U = CN_PAT_3Y_AFTER_DEAL_U;
    }

    public int getCN_PAT_3Y_AFTER_DEAL_I() {
        return CN_PAT_3Y_AFTER_DEAL_I;
    }

    public void setCN_PAT_3Y_AFTER_DEAL_I(int CN_PAT_3Y_AFTER_DEAL_I) {
        this.CN_PAT_3Y_AFTER_DEAL_I = CN_PAT_3Y_AFTER_DEAL_I;
    }

    public int getCN_PAT_3Y_AFTER_DEAL_A() {
        return CN_PAT_3Y_AFTER_DEAL_A;
    }

    public void setCN_PAT_3Y_AFTER_DEAL_A(int CN_PAT_3Y_AFTER_DEAL_A) {
        this.CN_PAT_3Y_AFTER_DEAL_A = CN_PAT_3Y_AFTER_DEAL_A;
    }

    public int getCN_PAT_5Y_AFTER_DEAL_ALL() {
        return CN_PAT_5Y_AFTER_DEAL_ALL;
    }

    public void setCN_PAT_5Y_AFTER_DEAL_ALL(int CN_PAT_5Y_AFTER_DEAL_ALL) {
        this.CN_PAT_5Y_AFTER_DEAL_ALL = CN_PAT_5Y_AFTER_DEAL_ALL;
    }

    public int getCN_PAT_5Y_AFTER_DEAL_U() {
        return CN_PAT_5Y_AFTER_DEAL_U;
    }

    public void setCN_PAT_5Y_AFTER_DEAL_U(int CN_PAT_5Y_AFTER_DEAL_U) {
        this.CN_PAT_5Y_AFTER_DEAL_U = CN_PAT_5Y_AFTER_DEAL_U;
    }

    public int getCN_PAT_5Y_AFTER_DEAL_I() {
        return CN_PAT_5Y_AFTER_DEAL_I;
    }

    public void setCN_PAT_5Y_AFTER_DEAL_I(int CN_PAT_5Y_AFTER_DEAL_I) {
        this.CN_PAT_5Y_AFTER_DEAL_I = CN_PAT_5Y_AFTER_DEAL_I;
    }

    public int getCN_PAT_5Y_AFTER_DEAL_A() {
        return CN_PAT_5Y_AFTER_DEAL_A;
    }

    public void setCN_PAT_5Y_AFTER_DEAL_A(int CN_PAT_5Y_AFTER_DEAL_A) {
        this.CN_PAT_5Y_AFTER_DEAL_A = CN_PAT_5Y_AFTER_DEAL_A;
    }

    public int getFR_PAT_AT_DEAL_ALL() {
        return FR_PAT_AT_DEAL_ALL;
    }

    public void setFR_PAT_AT_DEAL_ALL(int FR_PAT_AT_DEAL_ALL) {
        this.FR_PAT_AT_DEAL_ALL = FR_PAT_AT_DEAL_ALL;
    }

    public int getFR_PAT_AT_DEAL_U() {
        return FR_PAT_AT_DEAL_U;
    }

    public void setFR_PAT_AT_DEAL_U(int FR_PAT_AT_DEAL_U) {
        this.FR_PAT_AT_DEAL_U = FR_PAT_AT_DEAL_U;
    }

    public int getFR_PAT_AT_DEAL_I() {
        return FR_PAT_AT_DEAL_I;
    }

    public void setFR_PAT_AT_DEAL_I(int FR_PAT_AT_DEAL_I) {
        this.FR_PAT_AT_DEAL_I = FR_PAT_AT_DEAL_I;
    }

    public int getFR_PAT_AT_DEAL_A() {
        return FR_PAT_AT_DEAL_A;
    }

    public void setFR_PAT_AT_DEAL_A(int FR_PAT_AT_DEAL_A) {
        this.FR_PAT_AT_DEAL_A = FR_PAT_AT_DEAL_A;
    }

    public int getFR_PAT_3Y_AFTER_DEAL_ALL() {
        return FR_PAT_3Y_AFTER_DEAL_ALL;
    }

    public void setFR_PAT_3Y_AFTER_DEAL_ALL(int FR_PAT_3Y_AFTER_DEAL_ALL) {
        this.FR_PAT_3Y_AFTER_DEAL_ALL = FR_PAT_3Y_AFTER_DEAL_ALL;
    }

    public int getFR_PAT_3Y_AFTER_DEAL_U() {
        return FR_PAT_3Y_AFTER_DEAL_U;
    }

    public void setFR_PAT_3Y_AFTER_DEAL_U(int FR_PAT_3Y_AFTER_DEAL_U) {
        this.FR_PAT_3Y_AFTER_DEAL_U = FR_PAT_3Y_AFTER_DEAL_U;
    }

    public int getFR_PAT_3Y_AFTER_DEAL_I() {
        return FR_PAT_3Y_AFTER_DEAL_I;
    }

    public void setFR_PAT_3Y_AFTER_DEAL_I(int FR_PAT_3Y_AFTER_DEAL_I) {
        this.FR_PAT_3Y_AFTER_DEAL_I = FR_PAT_3Y_AFTER_DEAL_I;
    }

    public int getFR_PAT_3Y_AFTER_DEAL_A() {
        return FR_PAT_3Y_AFTER_DEAL_A;
    }

    public void setFR_PAT_3Y_AFTER_DEAL_A(int FR_PAT_3Y_AFTER_DEAL_A) {
        this.FR_PAT_3Y_AFTER_DEAL_A = FR_PAT_3Y_AFTER_DEAL_A;
    }

    public int getFR_PAT_5Y_AFTER_DEAL_ALL() {
        return FR_PAT_5Y_AFTER_DEAL_ALL;
    }

    public void setFR_PAT_5Y_AFTER_DEAL_ALL(int FR_PAT_5Y_AFTER_DEAL_ALL) {
        this.FR_PAT_5Y_AFTER_DEAL_ALL = FR_PAT_5Y_AFTER_DEAL_ALL;
    }

    public int getFR_PAT_5Y_AFTER_DEAL_U() {
        return FR_PAT_5Y_AFTER_DEAL_U;
    }

    public void setFR_PAT_5Y_AFTER_DEAL_U(int FR_PAT_5Y_AFTER_DEAL_U) {
        this.FR_PAT_5Y_AFTER_DEAL_U = FR_PAT_5Y_AFTER_DEAL_U;
    }

    public int getFR_PAT_5Y_AFTER_DEAL_I() {
        return FR_PAT_5Y_AFTER_DEAL_I;
    }

    public void setFR_PAT_5Y_AFTER_DEAL_I(int FR_PAT_5Y_AFTER_DEAL_I) {
        this.FR_PAT_5Y_AFTER_DEAL_I = FR_PAT_5Y_AFTER_DEAL_I;
    }

    public int getFR_PAT_5Y_AFTER_DEAL_A() {
        return FR_PAT_5Y_AFTER_DEAL_A;
    }

    public void setFR_PAT_5Y_AFTER_DEAL_A(int FR_PAT_5Y_AFTER_DEAL_A) {
        this.FR_PAT_5Y_AFTER_DEAL_A = FR_PAT_5Y_AFTER_DEAL_A;
    }
}

