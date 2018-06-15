package citationsearch.mapper;

import citationsearch.entity.Company;
import citationsearch.entity.PatStats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatStatsMapper extends Mapper {
    public int save(PatStats model) {
        //TODO:
        //Add foreign

        this.query = "INSERT INTO " + PatStats.TABLE + " (company_id, source_company_id, deal_date, " +
                "CN_PAT_AT_DEAL_ALL, CN_PAT_AT_DEAL_U, CN_PAT_AT_DEAL_I, CN_PAT_AT_DEAL_A," +
                "CN_PAT_3Y_AFTER_DEAL_ALL, CN_PAT_3Y_AFTER_DEAL_U, CN_PAT_3Y_AFTER_DEAL_I, CN_PAT_3Y_AFTER_DEAL_A," +
                "CN_PAT_5Y_AFTER_DEAL_ALL, CN_PAT_5Y_AFTER_DEAL_U, CN_PAT_5Y_AFTER_DEAL_I, CN_PAT_5Y_AFTER_DEAL_A," +
                "FR_PAT_AT_DEAL_ALL, FR_PAT_AT_DEAL_U, FR_PAT_AT_DEAL_I, FR_PAT_AT_DEAL_A," +
                "FR_PAT_3Y_AFTER_DEAL_ALL, FR_PAT_3Y_AFTER_DEAL_U, FR_PAT_3Y_AFTER_DEAL_I, FR_PAT_3Y_AFTER_DEAL_A," +
                "FR_PAT_5Y_AFTER_DEAL_ALL, FR_PAT_5Y_AFTER_DEAL_U, FR_PAT_5Y_AFTER_DEAL_I, FR_PAT_5Y_AFTER_DEAL_A" +
                ")"
                + " VALUES ("
                + model.getCompanyIdForSQL()
                + ", "
                + model.getSourceCompanyId()
                + ", \'"
                + model.getDealDate()
                + "\'" +
                "," + model.getCN_PAT_AT_DEAL_ALL() +
                "," + model.getCN_PAT_AT_DEAL_U() +
                "," + model.getCN_PAT_AT_DEAL_I() +
                "," + model.getCN_PAT_AT_DEAL_A() +
                "," + model.getCN_PAT_3Y_AFTER_DEAL_ALL() +
                "," + model.getCN_PAT_3Y_AFTER_DEAL_U() +
                "," + model.getCN_PAT_3Y_AFTER_DEAL_I() +
                "," + model.getCN_PAT_3Y_AFTER_DEAL_A() +
                "," + model.getCN_PAT_5Y_AFTER_DEAL_ALL() +
                "," + model.getCN_PAT_5Y_AFTER_DEAL_U() +
                "," + model.getCN_PAT_5Y_AFTER_DEAL_I() +
                "," + model.getCN_PAT_5Y_AFTER_DEAL_A() +

                "," + model.getFR_PAT_AT_DEAL_ALL() +
                "," + model.getFR_PAT_AT_DEAL_U() +
                "," + model.getFR_PAT_AT_DEAL_I() +
                "," + model.getFR_PAT_AT_DEAL_A() +
                "," + model.getFR_PAT_3Y_AFTER_DEAL_ALL() +
                "," + model.getFR_PAT_3Y_AFTER_DEAL_U() +
                "," + model.getFR_PAT_3Y_AFTER_DEAL_I() +
                "," + model.getFR_PAT_3Y_AFTER_DEAL_A() +
                "," + model.getFR_PAT_5Y_AFTER_DEAL_ALL() +
                "," + model.getFR_PAT_5Y_AFTER_DEAL_U() +
                "," + model.getFR_PAT_5Y_AFTER_DEAL_I() +
                "," + model.getFR_PAT_5Y_AFTER_DEAL_A() +
                ")";

        int result = this.executeOtherQuery();

        return result;
    }

    public int findStats(int companyId, String country, String date, String patentTypeIn) {
        int result = 0;

        String prefix;
        if ("CN".equals(country)) {
            prefix = "prefix = \'CN\'";
        } else {
            prefix = "prefix != \'CN\'";
        }

        String patentType;

        if("All".equals(patentTypeIn)){
            patentType = " (type = '1' or type = '2' or type = '3')";
        } else {
            patentType = " type = '" + patentTypeIn + "'";
        }

        this.query = "SELECT count(distinct docdb_family_id) FROM [unsw_bs_patent] WHERE " +
                "[company_id] = " + companyId + " AND " +
                prefix + " AND [publication_date] <= \'" + date + "\' AND"
                + patentType;

        System.out.println(query);

        ResultSet rs = this.executeGetQuery();

        try {
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<PatStats> getAllPatStats() {
        List<PatStats> patStatsList = new ArrayList<PatStats>();

        this.query = "select * from " + PatStats.TABLE;
        ResultSet rs = this.executeGetQuery();

        try {
            while (rs.next()) {
                PatStats patStats = new PatStats();
                patStats.setID(rs.getInt(1));
                patStats.setCompanyId(rs.getInt(2));
                patStats.setSourceCompanyId(rs.getInt(3));
                patStats.setDealDate(rs.getDate(4));

                patStats.setCN_PAT_AT_DEAL_ALL(rs.getInt(5));
                patStats.setCN_PAT_AT_DEAL_U(rs.getInt(6));
                patStats.setCN_PAT_AT_DEAL_I(rs.getInt(7));
                patStats.setCN_PAT_AT_DEAL_A(rs.getInt(8));
                patStats.setCN_PAT_3Y_AFTER_DEAL_ALL(rs.getInt(9));
                patStats.setCN_PAT_3Y_AFTER_DEAL_U(rs.getInt(10));
                patStats.setCN_PAT_3Y_AFTER_DEAL_I(rs.getInt(11));
                patStats.setCN_PAT_3Y_AFTER_DEAL_A(rs.getInt(12));
                patStats.setCN_PAT_5Y_AFTER_DEAL_ALL(rs.getInt(13));
                patStats.setCN_PAT_5Y_AFTER_DEAL_U(rs.getInt(14));
                patStats.setCN_PAT_5Y_AFTER_DEAL_I(rs.getInt(15));
                patStats.setCN_PAT_5Y_AFTER_DEAL_A(rs.getInt(16));

                patStats.setFR_PAT_AT_DEAL_ALL(rs.getInt(17));
                patStats.setFR_PAT_AT_DEAL_U(rs.getInt(18));
                patStats.setFR_PAT_AT_DEAL_I(rs.getInt(19));
                patStats.setFR_PAT_AT_DEAL_A(rs.getInt(20));
                patStats.setFR_PAT_3Y_AFTER_DEAL_ALL(rs.getInt(21));
                patStats.setFR_PAT_3Y_AFTER_DEAL_U(rs.getInt(22));
                patStats.setFR_PAT_3Y_AFTER_DEAL_I(rs.getInt(23));
                patStats.setFR_PAT_3Y_AFTER_DEAL_A(rs.getInt(24));
                patStats.setFR_PAT_5Y_AFTER_DEAL_ALL(rs.getInt(25));
                patStats.setFR_PAT_5Y_AFTER_DEAL_U(rs.getInt(26));
                patStats.setFR_PAT_5Y_AFTER_DEAL_I(rs.getInt(27));
                patStats.setFR_PAT_5Y_AFTER_DEAL_A(rs.getInt(28));

                patStatsList.add(patStats);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patStatsList;
    }
}


