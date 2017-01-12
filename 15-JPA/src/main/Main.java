package main;

import javax.persistence.*;

public class Main
{
   EntityManagerFactory emf;

   public static void main( String[] args )
   {
      (new Main()).test();
   }

   void test()
   {
      emf = Persistence.createEntityManagerFactory( "MeineJpaPU" );
      try {
         MeineDaten dat = new MeineDaten();
         dat.setMeinText( "Hallo WeltXXXX" );

         createEntity( dat );
         Object id = dat.getId();

         System.out.println( "\n--- " + readEntity( MeineDaten.class, id ) + " ---\n" );
      } finally {
         emf.close();
      }
   }

   public <T> void createEntity( T entity )
   {
      EntityManager     em = emf.createEntityManager();
      EntityTransaction tx = em.getTransaction();
      try {
         tx.begin();
         em.persist( entity );
         tx.commit();
      } catch( RuntimeException ex ) {
         if( tx != null && tx.isActive() ) tx.rollback();
         throw ex;
      } finally {
         em.close();
      }
   }

   public <T> T readEntity( Class<T> clss, Object id )
   {
      EntityManager em = emf.createEntityManager();
      try {
         return em.find( clss, id );
      } finally {
         em.close();
      }
   }
}