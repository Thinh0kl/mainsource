package com.girlkun.quayTamBao;

import com.girlkun.models.item.Item;
import com.girlkun.models.player.Player;
import com.girlkun.network.io.Message;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import java.util.List;



public class TamBaoService {
    public static TamBaoService instance;

    public static TamBaoService gI() {
        if (instance == null) {
            instance = new TamBaoService();
        }
        return instance;
    }

    public static List<Item> listItem = new ArrayList<Item>();
    public static List<Item> listItemVip = new ArrayList<Item>();
    public static int VAN_MAY = 60;

    public static void loadItem() {
        for (int i = 20; i < 50; i++) {
            Item item = ItemService.gI().createNewItem((short) i);
            listItem.add(item);
            Item itemVip = ItemService.gI().createNewItem((short) (i + 50));
            listItemVip.add(itemVip);
        }
    }

    public static void sendDataQuay(Player player,byte type) {
        Message msg = null;
        try {
            msg = new Message(70);
            msg.writer().writeByte(type);
            int size = listItem.size();
            msg.writer().writeInt(size);
            for (int i = 0; i < size; i++) {
                msg.writer().writeInt( type == 0 ? listItem.get(i).template.id : listItemVip.get(i).template.id);
                // MĂ u sáşŻc, Äáť táťŤ 0->7
                msg.writer().writeInt(Util.nextInt(7));
            }
            //Van may
            msg.writer().writeInt(20);
            //icon chia khoa
            msg.writer().writeInt(16005);
            msg.writer().writeInt(6763);
            player.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendListReceive(List<Item> list,Player player){
        try{
            Message msg = new Message(70);
            msg.writer().writeByte(1);
            int size = list.size();
            msg.writer().writeInt(size);
            for(int i=0;i<size;i++){
                msg.writer().writeInt(list.get(i).template.id);
                // MĂ u sáşŻc, Äáť táťŤ 0->7
                msg.writer().writeInt(6);
            }
            player.sendMessage(msg);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void readData(Message msg,Player player) {
        try {
            int type = msg.reader().readByte();
            if (type == 1 || type == 4) {
                int luotQuay = msg.reader().readInt();
                List<Item> receive = new ArrayList<>();
                for (int i = 0; i < luotQuay; i++) {
                    receive.add(type == 1 ? listItem.get(Util.nextInt(listItem.size() - 1)) : listItemVip.get(Util.nextInt(listItemVip.size() - 1)));
                }
                int dem = 0;
                int slKey = 0;
                for(Item it: player.inventory.itemsBag){
                    if(it == null || it.template == null){
                        dem++;
                        continue;
                    }
                    if((type == 1 && it.template.id == 2241) || (type == 4 && it.template.id == 2242)){
                        slKey += it.quantity;
                    }
                    
                }
                if(slKey < luotQuay){
                    Service.getInstance().sendThongBao(player, "BáşĄn khĂ´ng Äáť§ key Äáť quay");
                    return;
                }
                if(dem < luotQuay){
                    Service.getInstance().sendThongBao(player, "HĂ nh trang khĂ´ng Äáť§ cháť tráťng");
                    return;
                }else{
                    for(Item i : receive){
                        InventoryServiceNew.gI().addItemBag(player, i);
                    }
                    for(Item it: player.inventory.itemsBag){
                        if(slKey == 0) break;
                        if(it == null || it.template == null) continue;
                        if((type == 1 && it.template.id == 2241) || (type == 4 && it.template.id == 2242)){
                            int min = Math.min(luotQuay, it.quantity);
                            luotQuay -= min;
                            it.quantity -= min;
                        }
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    sendListReceive(receive, player);
                }
            }else if(type == 0 || type == 3){
                sendDataQuay(player,(byte)type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
