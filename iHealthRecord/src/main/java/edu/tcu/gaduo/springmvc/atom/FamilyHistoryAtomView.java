package edu.tcu.gaduo.springmvc.atom;

import java.util.List;
import java.util.Map;

import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Feed;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

public class FamilyHistoryAtomView extends AbstractAtomFeedView{

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Feed feed, HttpServletRequest request) {
        feed.setId("tag:springsource.org");
        feed.setTitle("Veterinarians");
        //feed.setUpdated(date);
    }

    @Override
    protected List<Entry> buildFeedEntries(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {

//        Vets vets = (Vets) model.get("vets");
//        List<Vet> vetList = vets.getVetList();
//        List<Entry> entries = new ArrayList<Entry>(vetList.size());
//
//        for (Vet vet : vetList) {
//            Entry entry = new Entry();
//            // see http://diveintomark.org/archives/2004/05/28/howto-atom-id#other
//            entry.setId(String.format("tag:springsource.org,%s", vet.getId()));
//            entry.setTitle(String.format("Vet: %s %s", vet.getFirstName(), vet.getLastName()));
//            //entry.setUpdated(visit.getDate().toDate());
//
//            Content summary = new Content();
//            summary.setValue(vet.getSpecialties().toString());
//            entry.setSummary(summary);
//
//            entries.add(entry);
//        }
//        response.setContentType("blabla");
        return null;

    }
}
