/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.rest.persistence;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.aerogear.rest.common.RandomStringUtilities;
import org.jboss.aerogear.rest.model.Member;

public class PersistenceSimulator {

    private static Hashtable<String, Member> ht = new Hashtable<String, Member>();

    public static final Member addMember(Member m) {
        m.setId(RandomStringUtilities.generateRandomId(20));
        ht.put(m.getId(), m);
        return m;
    }

    public static final Member updateMember(Member m) {
        ht.put(m.getId(), m);
        return m;
    }

    public static final void removeMember(String id) {
        ht.remove(id);
    }

    public static final List<Member> getMembers(String limit, String id) {
        final List<Member> list = (new ArrayList<Member>());

        if (id != null && !id.trim().equals("")) {
            list.add(ht.get(id));
            return list;
        } else if (!StringUtils.isEmpty(limit)) {
            Iterator<String> iter = ht.keySet().iterator();
            while (iter.hasNext()) {

                if (list.size() >= Integer.parseInt(limit))
                    break;

                final String key = iter.next();
                final Member member = ht.get(key);

                list.add(member);

            }
            return list;
        }

        list.addAll(ht.values());
        return list;
    }

    public static final List<Member> getMembersByLimitAndDesc(String limit, String desc) {
        final List<Member> list = (new ArrayList<Member>());

        if (!StringUtils.isEmpty(desc)) {

            Iterator<String> iter = ht.keySet().iterator();
            while (iter.hasNext()) {

                if (list.size() >= Integer.parseInt(limit)) {
                    break;
                }

                final String key = iter.next();
                final Member member = ht.get(key);
                if (desc.equals(member.getDescription())) {
                    list.add(member);
                }
            }

            return list;
        } else if (!StringUtils.isEmpty(limit)) {
            Iterator<String> iter = ht.keySet().iterator();
            while (iter.hasNext()) {

                if (list.size() >= Integer.parseInt(limit))
                    break;

                final String key = iter.next();
                final Member member = ht.get(key);

                list.add(member);

            }
            return list;
        }

        list.addAll(ht.values());
        return list;
    }
}
