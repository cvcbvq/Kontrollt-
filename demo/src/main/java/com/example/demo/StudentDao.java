import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class StudentDao {

    // a) Uue üliõpilase salvestamine andmebaasi
    public void saveStudent(EntityManagerFactory emf, String name, int age, String email) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Student student = new Student();
            student.setName(name);
            student.setAge(age);
            student.setEmail(email);

            em.persist(student); // Salvestab olemiklassi andmebaasi

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close(); // EntityManager tuleb alati sulgeda
        }
    }

    // b) Üliõpilase leidmine id järgi ja vanuse muutmine
    public void updateStudentAge(EntityManagerFactory emf, Long id, int newAge) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Student student = em.find(Student.class, id); // Otsib id järgi
            if (student != null) {
                student.setAge(newAge); // Muudab vanust
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // c) Üliõpilase kustutamine id järgi
    public void deleteStudent(EntityManagerFactory emf, Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Student student = em.find(Student.class, id); // Otsib üles, et eemaldada
            if (student != null) {
                em.remove(student); // Kustutab andmebaasist
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}