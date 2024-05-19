package com.girlkun.models.boss.list_boss.Doraemon;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;
import java.util.Random;


public class Doraemon extends Boss {

    public Doraemon() throws Exception {
        super(BossID.DORAEMON, BossesData.DORAEMON);
    }
    @Override
    public void reward(Player plKill) {
        int[] itemDos = new int[]{563,565,567,561,656};
        if (Util.isTrue(25, 100)) {
            ItemMap it = new ItemMap(this.zone, itemDos[Util.nextInt(0,4)], 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id);

            if(itemDos[Util.nextInt(0,4)] == 561 || itemDos[Util.nextInt(0,4)] == 656 ){
                it.options.add(new Item.ItemOption(14, Util.nextInt(20,30)));
            }else{
                it.options.add(new Item.ItemOption(23, Util.nextInt(40,100)));
            }
            
            Service.gI().dropItemMap(this.zone, it);
        } else {
            Service.gI().dropItemMap(this.zone, new ItemMap(zone,14, 1, this.location.x, zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id));
        }

        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }

  
   @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
        if(Util.canDoWithTime(st,900000)){
            this.changeStatus(BossStatus.LEAVE_MAP);
        }
    }
    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st= System.currentTimeMillis();
    }
    private long st;

  
    
    }
/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - GirlBeo
 */
    