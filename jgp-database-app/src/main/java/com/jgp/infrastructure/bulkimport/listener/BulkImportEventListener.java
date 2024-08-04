package com.jgp.infrastructure.bulkimport.listener;

import com.jgp.infrastructure.bulkimport.constants.TemplatePopulateImportConstants;
import com.jgp.infrastructure.bulkimport.event.BulkImportEvent;
import com.jgp.infrastructure.bulkimport.importhandler.ImportHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BulkImportEventListener {

    private final ApplicationContext applicationContext;

    @EventListener
   public void handleBulkImportEvent(BulkImportEvent bulkImportEvent){
        ImportHandler importHandler = null;
        switch (bulkImportEvent.entityType()){
            case TemplatePopulateImportConstants.BMO_ENTITY:
                importHandler = this.applicationContext.getBean("BMOImportHandler", ImportHandler.class);
                break;
            default:
                throw new IllegalArgumentException( "Unable to find requested resource");
        }

        importHandler.process(bulkImportEvent);
    }
}
