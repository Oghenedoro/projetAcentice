package cm.acentice.ideale.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class GeneratorIdFournisseurMP implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        String prefix = "";
        String suffix = "";

        try {
            Connection conn = session.connection();
            Statement st = conn.createStatement();

            String sqlMatierePremiere = "SELECT nextval('fournisseurmp_seq'); ";
            ResultSet res = st.executeQuery(sqlMatierePremiere);
            if(res.next()) {
                prefix = "F_MP";
                int seqval = res.getInt(1);
                suffix = String.valueOf(seqval);

            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return prefix + suffix;
    }

}
