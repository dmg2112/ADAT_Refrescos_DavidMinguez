package accesoDatos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.mapping.List;

import auxiliares.HibernateUtil;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class HibernateManager implements I_Acceso_Datos {
	private Session session = new HibernateUtil().getSessionFactory().openSession();

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> depositos = new HashMap<Integer, Deposito>();
		java.util.List results = session.createQuery("Select dep from Deposito dep").list();
		Iterator it = results.iterator();

		while (it.hasNext()) {

			Deposito tmp = (Deposito) it.next();
			depositos.put(tmp.getValor(), tmp);
		}

		return depositos;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> dispensadores = new HashMap<String, Dispensador>();
		java.util.List results = session.createQuery("Select disp from Dispensador disp").list();
		Iterator it = results.iterator();

		while (it.hasNext()) {

			Dispensador tmp = (Dispensador) it.next();
			dispensadores.put(tmp.getClave(), tmp);
		}

		return dispensadores;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		Transaction trans = session.beginTransaction();
		try {

			Iterator it = depositos.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry ent = (Map.Entry) it.next();
				Deposito tmp = (Deposito) ent.getValue();
				session.update(tmp);

			}

		} catch (Exception e) {
			trans.rollback();

			trans.commit();
			e.printStackTrace();
			return false;

		}
		trans.commit();
		return true;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		Transaction trans = session.beginTransaction();
		try {

			Iterator it = dispensadores.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry ent = (Map.Entry) it.next();
				Dispensador tmp = (Dispensador) ent.getValue();
				session.update(tmp);

			}

		} catch (Exception e) {
			trans.rollback();

			trans.commit();
			e.printStackTrace();
			return false;

		}
		trans.commit();
		return true;
	}
}
