package com.girlkun.models.boss.list_boss.Cooler;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.server.Manager;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.PetService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;

import java.util.Random;

public class Morimata extends Boss {

    public Morimata() throws Exception {
        super(Util.randomBossId(), BossesData.Morimata);
    }

    @Override
    public void reward(Player plKill) {
        if (plKill != null && plKill.getSession().actived) {
                if (plKill.pet == null) {
                    PetService.gI().createNormalPet(plKill);
                    Service.gI().sendThongBao(plKill, "Bạn vừa nhận được đệ tử");
                } else {
                    Service.gI().sendThongBao(plKill, "Bạn đã có đệ rồi");
                }
                Service.gI().dropItemMap(this.zone, new ItemMap(zone, 2115, 1, this.location.x, zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id));
    }
    }
        


    @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
        if (Util.canDoWithTime(st, 1200000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
        }
    }
    @Override
    public long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = damage/2;
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
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }

    private long st;

}

