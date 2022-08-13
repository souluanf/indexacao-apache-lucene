package dev.luanfernandes.lucene;

import dev.luanfernandes.lucene.controller.LuceneIndexer;
import dev.luanfernandes.lucene.controller.LuceneSearcher;
import dev.luanfernandes.lucene.model.ShoppingList;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.status.StatusLogger;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class Application {
    static final String LINE = "----------------------------------------------";
    public static void main(String[] args) throws IOException {
        StatusLogger.getLogger().setLevel(Level.OFF);
        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            Directory index = new ByteBuffersDirectory();
            LuceneIndexer indexer = container.select(LuceneIndexer.class).get();
            LuceneSearcher searcher = container.select(LuceneSearcher.class).get();
            indexer.index(index);
            log.info(LINE);
            log.info("TODAS AS LISTAS: ");
            log.info(LINE);
            searcher.findAll(index).forEach(Application::printShoppingList);
            log.info("POR NOME DA PESSOA: ");
            log.info(LINE);
            searcher.findByPersonName(index, "Camila Alvarenga").forEach(Application::printShoppingList);
            log.info("POR NOME DE ITEM CONTIDO NA LISTA: ");
            log.info(LINE);
            searcher.findByItem(index, "Mussarela").forEach(Application::printShoppingList);
            log.info("POR INTERVALO DE TEMPO: ");
            log.info(LINE);
            searcher.findByDateRange(index, LocalDate.of(2020, 1, 1),
                    LocalDate.of(2020, 1, 2)).forEach(Application::printShoppingList);
        }
    }

    private static void printShoppingList(ShoppingList shoppingList) {
        log.info("Arquivo: " + shoppingList.getFileName());
        log.info("Nome: " + shoppingList.getName());
        log.info("Data: " + shoppingList.getDate().format(DateTimeFormatter.ISO_DATE));
        log.info("Items: " + String.join(", ", shoppingList.getItems()));
        log.info(LINE);
    }
}
