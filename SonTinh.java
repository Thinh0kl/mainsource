/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlkun.models.boss.list_boss.Mabu12h;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import com.girlkun.services.func.*;
/**
 * @@Stole By ASOOME //
 */
public class SonTinh extends Boss {

    public SonTinh() throws Exception {
        super(Util.randomBossId(), BossesData.SONTINH);
        this.cFlag = 2;
    }

    @Override
    public void reward(Player plKill) {
//        short[] itemRan = Manager.itemIds_bossVuaHung;
//        short itemId = itemRan[0];
//        short idRanDom = (short) Util.nextInt(100);
//        if (idRanDom < 70) {
//            itemId = itemRan[Util.nextInt(itemRan.length - 1)];
//        } else if (idRanDom < 95) {
//            itemId = itemRan[Util.nextInt(itemRan.length)];
//        } else if (idRanDom < 100) {
//            itemId = 674;
//        }
//        int ranSL = Util.nextInt(1, 10);
//
//        if (itemId == 674) {
//            ranSL = Util.nextInt(1, 5);
//        }
//        for (int i = 0; i < ranSL; ++i) {
//            ItemMap it = new ItemMap(this.zone, itemId, 1, this.location.x + i * 20, this.zone.map.yPhysicInTop(this.location.x + i * 20,
//                    this.location.y - 24), plKill.id);
//            Service.getInstance().dropItemMap(this.zone, it);
//        }

        //TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }

    @Override
    public void active() {

        super.active(); //To change body of generated methods, choose Tools | Templates.
//        if(Util.canDoWithTime(st,900000)){
//            this.changeStatus(BossStatus.LEAVE_MAP);
//        }
    }

    @Override
    public void joinMap() {

        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
        Service.getInstance().changeFlag(this, 2);
    }
    private long st;

    @Override
    public long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = 1;
            }
            if (damage >= 1000000) {
                damage = 1000000;
            }
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }
            return damage;
        } else {
            return 0;
        }
    }

    @Override
    public void wakeupAnotherBossWhenDisappear() {
        if (this.parentBoss != null) {
            this.parentBoss.changeToTypePK();
        }
    }

}
