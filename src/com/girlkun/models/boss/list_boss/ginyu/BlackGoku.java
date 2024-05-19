package com.girlkun.models.boss.list_boss.ginyu;

import com.girlkun.consts.ConstTask;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;
import java.util.Random;


public class BlackGoku extends Boss {
    int rewardIndex = 0;
    public BlackGoku() throws Exception {
        super(BossID.BlackGoku, BossesData.BLACK_GOKU, BossesData.SUPER_BLACK_GOKU, BossesData.ZAMAS, BossesData.ZAMASFUSION);
    }

    @Override
    public void moveTo(int x, int y) {
        if (this.currentLevel == 1) {
            return;
        }
        super.moveTo(x, y);
    }

     @Override
    public void reward(Player plKill) {


        if(rewardIndex >= 4){
            rewardIndex = 0;
        }
        int[] itemDos = new int[]{992,865,874,725};
        int[] itemDos2 = new int[]{562,564,566};
        ItemMap mvbt = new ItemMap(this.zone, itemDos[rewardIndex], 1, this.location.x, this.location.y, plKill.id);         
        TaskService.gI().checkDoneTaskPickItem(plKill, mvbt);
        Service.gI().dropItemMap(this.zone, mvbt);
        rewardIndex += 1;

      if(Util.isTrue(20,100)){
        ItemMap it = new ItemMap(this.zone, itemDos2[Util.nextInt(0,3)], 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id);
        it.options.add(new Item.ItemOption(0, Util.nextInt(3000,6500)));
        Service.gI().dropItemMap(this.zone, it);
      }
    }

    @Override
    protected void notifyJoinMap() {
        if (this.currentLevel == 1) {
            return;
        }
        super.notifyJoinMap();
    }
    @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
        if (Util.canDoWithTime(st, 900000)) {//9000000000
            this.changeStatus(BossStatus.LEAVE_MAP);
        }
    }

    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }
    private long st;
}

/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - GirlBeo
 */
