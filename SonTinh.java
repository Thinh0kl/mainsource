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
// public class SonTinh extends Boss {
// private long lastTimeHapThu;
//     private int timeHapThu;
//     public SonTinh() throws Exception {

//         super(BossID.SONTINH, BossesData.SONTINH);
//     }
//     @Override
//     public void reward(Player plKill) {
//         if(Util.isTrue(30,100)){
//         ItemMap it = new ItemMap(this.zone, 1230, 1, this.location.x, this.location.y, plKill.id);
//         Service.gI().dropItemMap(this.zone, it);
//         TaskService.gI().checkDoneTaskKillBoss(plKill, this);
//     }else  if(Util.isTrue(10,100)){
//          ItemMap it = new ItemMap(this.zone, 16, 1, this.location.x, this.location.y, plKill.id);
//         Service.gI().dropItemMap(this.zone, it);
//         }
//          ItemMap it = new ItemMap(this.zone, 17, 1, this.location.x, this.location.y, plKill.id);
//          Service.gI().dropItemMap(this.zone, it);
//         if (TaskService.gI().getIdTask(plKill) == ConstTask.TASK_27_3) {
//             Service.gI().dropItemMap(this.zone, new ItemMap(zone, 16, 1, this.location.x, zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id));
//         }
//     }
//     @Override
//     public void active() {
//         if (this.typePk == ConstPlayer.NON_PK) {
//             this.changeToTypePKPvP();
//         }
//         //this.hapThu();
//         this.attack();
//     }
//     private void hapThu() {
//         if (!Util.canDoWithTime(this.lastTimeHapThu, this.timeHapThu) || !Util.isTrue(1, 100)) {
//             return;
//         }
//         Player pl = this.zone.getRandomPlayerInMap();
//         if (pl == null || pl.isDie()) {
//             return;
//         }
// //        ChangeMapService.gI().changeMapYardrat(this, this.zone, pl.location.x, pl.location.y);
//         this.nPoint.dameg += (pl.nPoint.dame * 5 / 100);
// //        this.nPoint.hpg += (pl.nPoint.hp * 2 / 100);
//         this.nPoint.hpg = this.nPoint.hpg + (pl.nPoint.hp / 50L) > 2_000_000_000 ? 2_000_000_000 : (int) (this.nPoint.hpg + (pl.nPoint.hp / 50));
//         this.nPoint.critg++;
//         this.nPoint.calPoint();
//         PlayerService.gI().hoiPhuc(this, pl.nPoint.hp, 0);
//         pl.injured(null, pl.nPoint.hpMax, true, false);
//         Service.gI().sendThongBao(pl, "Bạn vừa bị " + this.name + " hấp thu!");
//         this.chat(2, "Ui cha cha, kinh dị quá. " + pl.name + " vừa bị tên " + this.name + " nuốt chửng kìa!!!");
//         this.chat("Haha, ngọt lắm đấy " + pl.name + "..");
//         this.lastTimeHapThu = System.currentTimeMillis();
//         this.timeHapThu = Util.nextInt(30000, 70000);
//     }
//    @Override
//     public long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
//         if (Util.isTrue(70, 100) && plAtt != null) {//tỉ lệ hụt của thiên sứ
//             Util.isTrue(this.nPoint.tlNeDon, 100000);
//             if (Util.isTrue(1, 100)) {
//                 this.chat("Ta Chính Là Thần SooMe");
//                 this.chat("Ta Chính Là Thần SooMe");
//             } else if (Util.isTrue(1, 100)) {
//                 this.chat("Ta Chính Là Thần SooMe");
//                 this.chat("Chỉ cần hoàn thiện nó!");
//                 this.chat("Các ngươi sẽ tránh được mọi nguy hiểm");
//             } else if (Util.isTrue(1, 100)) {
//                 this.chat("Ta Chính Là Thần SooMe");
//             }
//             damage = 0;
//         }
//         if (!this.isDie()) {
//             if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1)) {
//                 this.chat("Xí hụt");
//                 return 0;
//             }
//             damage = this.nPoint.subDameInjureWithDeff(damage);
//             if (!piercing && effectSkill.isShielding) {
//                 if (damage > nPoint.hpMax) {
//                     EffectSkillService.gI().breakShield(this);
//                 }
//                 damage = damage;
//                  if (damage > nPoint.mpMax) {
//                     EffectSkillService.gI().breakShield(this);
//                 }
//                 damage = damage; 
//                  if (damage > nPoint.tlNeDon) {
//                     EffectSkillService.gI().breakShield(this);
//                 }
//                 damage = damage; 
//             }
//             if (damage >= 1000000) {
//                 damage = 10000000;
//             }
//             this.nPoint.subHP(damage);
//             if (isDie()) {
//                 this.setDie(plAtt);
//                 die(plAtt);
//             }
//             return damage;
//         } else {
//             return 0;
//         }
//     }
//     @Override
//     public void joinMap() {
//         super.joinMap(); //To change body of generated methods, choose Tools | Templates.
//         st = System.currentTimeMillis();
//     }
//     private long st;
//     @Override
//     public void leaveMap() {
//         super.leaveMap();
//         BossManager.gI().removeBoss(this);
//         super.dispose();
//     }
// }
