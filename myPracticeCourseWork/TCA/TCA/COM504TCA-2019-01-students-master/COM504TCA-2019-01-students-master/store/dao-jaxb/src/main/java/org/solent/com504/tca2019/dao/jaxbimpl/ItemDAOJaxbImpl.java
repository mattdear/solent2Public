/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.tca2019.dao.jaxbimpl;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.com504.tca2019.model.Item;
import org.solent.com504.tca2019.model.ItemDAO;
import org.solent.com504.tca2019.model.ItemList;

/**
 *
 * @author cgallen
 */
public class ItemDAOJaxbImpl implements ItemDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ItemDAOJaxbImpl.class);

    // jaxb context needs jaxb.index jaxbFile to be in same classpath
    // this path contains a list of Jaxb annotated classes for the context to parse
    private static final String CONTEXT_PATH = "org.solent.com504.tca2019.model";

    // you must obtain lock before accessing objects and / or saving file
    public final Object Lock = new Object();

    private String dataFileLocation = null;

    private File jaxbFile = null;

    private ItemList itemList = null;

    private JAXBContext jaxbContext = null;

    public ItemDAOJaxbImpl(String dataFileLocation) {
        super();
        if (dataFileLocation == null) {
            throw new IllegalArgumentException("dataFile cannot be null");
        }
        this.dataFileLocation = dataFileLocation;
        load();
    }

    @Override
    public Item createItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        synchronized (Lock) {
            // set initial id if not set or increment by 1
            Integer id = (itemList.getLastItemId() == null) ? 1 : itemList.getLastItemId() + 1;

            itemList.setLastItemId(id);
            Item ecopy = copy(item);
            ecopy.setId(id);
            itemList.getItems().add(ecopy);
            save();
            return ecopy;
        }
    }

    @Override
    public boolean deleteItem(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        synchronized (Lock) {
            Iterator<Item> it = itemList.getItems().iterator();
            while (it.hasNext()) {
                Item en = it.next();
                if (id.equals(en.getId())) {
                    it.remove();
                    save();
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public void deleteAllEntities() {
        synchronized (Lock) {
            itemList.getItems().clear();
        }
    }

    @Override
    public Item retrieveItem(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        synchronized (Lock) {
            for (Item en : itemList.getItems()) {
                if (id.equals(en.getId())) {
                    return copy(en);
                }
            }
        }
        return null;
    }

    @Override
    public List<Item> retrieveAllEntities() {
        synchronized (Lock) {
            List<Item> returnList = new ArrayList<Item>();
            for (Item item : itemList.getItems()) {
                returnList.add(copy(item));
            };
            return returnList;
        }
    }

    /**
     * Returns a list of all Entities which match all of the set (i.e. not null)
     * fields of itemTemplate
     *
     * @param itemTemplate
     * @return
     */
    @Override
    public List<Item> retrieveMatchingEntities(Item itemTemplate) {
        if (itemTemplate == null) {
            throw new IllegalArgumentException("itemTemplate cannot be null");
        }
        List<Item> returnList = new ArrayList<Item>();
        synchronized (Lock) {
            for (Item item : itemList.getItems()) {
                boolean match = true;
                if (itemTemplate.getId() != null) {
                    if (!itemTemplate.getId().equals(item.getId())) {
                        match = false;
                    }
                };
                if (itemTemplate.getSku() != null) {
                    if (!itemTemplate.getSku().equals(item.getSku())) {
                        match = false;
                    }
                };
                if (itemTemplate.getDescription() != null) {
                    if (!itemTemplate.getDescription().equals(item.getDescription())) {
                        match = false;
                    }
                };
                if (itemTemplate.getPrice() != null) {
                    if (!itemTemplate.getPrice().equals(item.getPrice())) {
                        match = false;
                    }
                };
                if (itemTemplate.getQuantity() != null) {
                    if (!itemTemplate.getQuantity().equals(item.getQuantity())) {
                        match = false;
                    }
                };
                if (match) {
                    returnList.add(copy(item));
                }
            }
            return returnList;
        }
    }

    @Override
    public Item updateItem(Item itemTemplate) {
        if (itemTemplate == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        synchronized (Lock) {
            for (Item en : itemList.getItems()) {
                if (itemTemplate.getId().equals(en.getId())) {
                    boolean changedfield = false;

                    // update properties fields if only if itemTemplate field is set
                    if (itemTemplate.getSku() != null) {
                        en.setSku(itemTemplate.getSku());
                        changedfield = true;
                    }
                    if (itemTemplate.getDescription() != null) {
                        en.setDescription(itemTemplate.getDescription());
                        changedfield = true;
                    }
                    if (itemTemplate.getPrice() != null) {
                        en.setPrice(itemTemplate.getPrice());
                        changedfield = true;
                    }
                    if (itemTemplate.getQuantity() != null) {
                        en.setQuantity(itemTemplate.getQuantity());
                        changedfield = true;
                    }
                    // save if anything changed
                    if (changedfield) {
                        save();
                    }
                    return copy(en);
                }

            }
        }
        return null; //item not found
    }

    /**
     * copies new Item data transfer objects to create detached object and so
     * avoid problems with indirect object modification
     *
     * @param item
     * @return independent copy of Item
     */
    private Item copy(Item item) {
        try {
            StringWriter sw1 = new StringWriter();
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(item, sw1);

            StringReader sr1 = new StringReader(sw1.toString());
            Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
            Item newAccount = (Item) jaxbUnMarshaller.unmarshal(sr1);
            return newAccount;
        } catch (JAXBException ex) {
            throw new RuntimeException("problem copying jaxb object", ex);
        }
    }

    /**
     * loads jaxb file at beginning of program
     */
    private void load() {

        try {

            // jaxb context needs jaxb.index jaxbFile to be in same classpath
            // this contains a list of Jaxb annotated classes for the context to parse
            jaxbContext = JAXBContext.newInstance(CONTEXT_PATH);

            // try to load dataFileLocation
            jaxbFile = new File(dataFileLocation);
            LOG.debug("using dataFile:" + jaxbFile.getAbsolutePath());

            if (jaxbFile.exists()) {
                LOG.debug("dataFile exists loading:" + jaxbFile.getAbsolutePath());
                // load jaxbFile
                Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
                itemList = (ItemList) jaxbUnMarshaller.unmarshal(jaxbFile);
            } else {
                // create annd save an empty jaxbFile
                LOG.debug("dataFile does not exist creating new " + jaxbFile.getAbsolutePath());

                itemList = new ItemList();

                // make directories if dont exist
                jaxbFile.getParentFile().mkdirs();

                // save empty data to new file
                save();
            }

        } catch (JAXBException ex) {
            throw new RuntimeException("problem creating persistor", ex);
        }

    }

    /**
     * saves data to datafile on updates
     */
    private void save() {
        try {
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(itemList, jaxbFile);
            if (LOG.isDebugEnabled()) {
                StringWriter sw1 = new StringWriter();
                jaxbMarshaller.marshal(itemList, sw1);
                LOG.debug("saving xml to file:" + sw1.toString());
            }

        } catch (JAXBException ex) {
            throw new RuntimeException("problem persisting dao", ex);
        }
    }

}
