package projectH.HistoricalDatabaseofCaptives.StandbyServices;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class PostgresQueryFactory {
    public static void queryExecuter(String SqlQuery, String parameterOne) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BPATool");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createNativeQuery(SqlQuery);
        query.setParameter(1, parameterOne.toString());
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}