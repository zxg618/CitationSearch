package citationsearch.mapper;

import citationsearch.entity.CitStats;
import citationsearch.entity.PatStats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitStatsMapper extends Mapper {
    public int save(CitStats model) {
        this.query = "INSERT INTO " + CitStats.TABLE + " (company_id, source_company_id, deal_date, " +
                "CN_CIT_AT_DEAL_ON_PAT_AT_DEAL, FR_CIT_AT_DEAL_ON_PAT_AT_DEAL, CN_CIT_AT_2016_ON_PAT_AT_DEAL, FR_CIT_AT_2016_ON_PAT_AT_DEAL," +
                "CN_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL, FR_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL, CN_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL, FR_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL" +
                ")"
                + " VALUES ("
                + model.getCompanyIdForSQL()
                + ", "
                + model.getSourceCompanyId()
                + ", \'"
                + model.getDealDate()
                + "\'" +
                "," + model.getCN_CIT_AT_DEAL_ON_PAT_AT_DEAL() +
                "," + model.getFR_CIT_AT_DEAL_ON_PAT_AT_DEAL() +
                "," + model.getCN_CIT_AT_2016_ON_PAT_AT_DEAL() +
                "," + model.getFR_CIT_AT_2016_ON_PAT_AT_DEAL() +
                "," + model.getCN_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL() +
                "," + model.getFR_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL() +
                "," + model.getCN_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL() +
                "," + model.getFR_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL() +
                ")";

        int result = this.executeOtherQuery();

        return result;
    }

    public int findStats(int companyId, String citCountry, String citDate, String patDate) {
        int result = 0;

        String citCountryQuery;
        if ("CN".equals(citCountry)) {
            citCountryQuery = "[citing_prefix] = \'CN\'";
        } else {
            citCountryQuery = "[citing_prefix] != \'CN\'";
        }

        String citDateQuery;
        if ("".equals(citDate)) {
            citDateQuery = "";
        } else {
            citDateQuery = " AND [citing_publication_date] <= \'" + citDate + "\'";
        }


        this.query = "SELECT count(distinct citing_application_docdb_family_id) FROM [unsw_bs_citation] WHERE " +
                citCountryQuery +
                citDateQuery +
                " AND [patent_id] IN" +
                "   (SELECT id FROM [unsw_bs_patent] WHERE [company_id] = " + companyId +
                "       AND [publication_date] <= \'" + patDate + "\')";

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

    public List<CitStats> getAllCitStats() {
        List<CitStats> citStatsList = new ArrayList<>();

        this.query = "select * from " + CitStats.TABLE;
        ResultSet rs = this.executeGetQuery();

        try {
            while (rs.next()) {
                CitStats citStats = new CitStats();
                citStats.setID(rs.getInt(1));
                citStats.setCompanyId(rs.getInt(2));
                citStats.setSourceCompanyId(rs.getInt(3));
                citStats.setDealDate(rs.getDate(4));

                citStats.setCN_CIT_AT_DEAL_ON_PAT_AT_DEAL(rs.getInt(5));
                citStats.setFR_CIT_AT_DEAL_ON_PAT_AT_DEAL(rs.getInt(6));
                citStats.setCN_CIT_AT_2016_ON_PAT_AT_DEAL(rs.getInt(7));
                citStats.setFR_CIT_AT_2016_ON_PAT_AT_DEAL(rs.getInt(8));
                citStats.setCN_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL(rs.getInt(9));
                citStats.setFR_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL(rs.getInt(10));
                citStats.setCN_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL(rs.getInt(11));
                citStats.setFR_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL(rs.getInt(12));

                citStatsList.add(citStats);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return citStatsList;
    }
}


