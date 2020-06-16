/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.iga.project.service;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author asus
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    public String initQuery(String item) {
        return "SELECT  " + item + " FROM " + entityClass.getSimpleName() + " " + item + " WHERE 1=1";
    }

    public String initQuery() {
        return initQuery("item");
    }

    public T findOne(String key, String value) {
        return findOne("item", key, value);
    }

    public T findOne(String item, String key, String value) {
        List<T> list = findMultiple(item, key, value);
        return getFirst(list);
    }

    public List<T> findMultiple(String key, String value) {
        return findMultiple("item", key, value);
    }

    public List<T> findMultiple(String item, String key, String value) {
        String query = initQuery(item);
        query += addConstraint(item, key, value, "=");
        return findMultiple(query);
    }

    public T findOne(String query) {
        List<T> result = findMultiple(query);
        return getFirst(result);
    }

    private T getFirst(List<T> result) {
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public List<T> findMultiple(String query) {
        List<T> result = getEntityManager().createQuery(query).getResultList();
        return result;
    }

    

    public String addConstraint(String item, String key, String value, String operator) {
        if (value != null && !value.isEmpty()) {
            String compare = "= '" + value + "'";
            if (operator.equals("LIKE")) {
                compare = " LIKE '%" + value + "%'";
            }
            return " AND " + item + "." + key + compare;
        } else {
            return "";
        }
    }

    public String addConstraint(String key, String value) {
        return addConstraint("item", key, value, "=");
    }

    public String addConstraintLike(String key, String value) {
        return addConstraint("item", key, value, "LIKE");
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
