package com.girlkun.models.boss.list_boss.Cooler;

import com.girlkun.models.boss.*;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;

public class goku5 extends Boss {

    public goku5() throws Exception {
        super(BossID.GOKU5, BossesData.GOKU5);
    }

    @Override
    public void reward(Player plKill) {
            int randomSKH = Util.nextInt(127,135);
        if (Util.isTrue(50, 100)) {
            if(Util.isTrue(5,100)){
                int randomTC = Util.nextInt(500,700); 
                ItemMap mvbt = new ItemMap(this.zone, 2214, 1, this.location.x, this.location.y, plKill.id);
                mvbt.options.add(new Item.ItemOption(220, randomTC));
                mvbt.options.add(new Item.ItemOption(21, 400));// giap
                mvbt.options.add(new Item.ItemOption(36, 0)); // hoac 128 , 129
                mvbt.options.add(new Item.ItemOption(76, 0)); // hoac 140 ,141
                mvbt.options.add(new Item.ItemOption(randomSKH, 0)); // hoac 140 ,141
                mvbt.options.add(new Item.ItemOption(randomSKH >= 133 ? randomSKH + 3 : randomSKH + 12, 0)); // hoac 140 ,141
                Service.gI().dropItemMap(this.zone, mvbt);
            }else{
                int randomITEM = Util.nextInt(2212,2216);
            
                ItemMap mvbt = new ItemMap(this.zone, randomITEM, 1, this.location.x, this.location.y, plKill.id);
                switch(randomITEM){
                    case 2212 :
                        int randomGIAP = Util.nextInt(4000,10000);
                         mvbt.options.add(new Item.ItemOption(47, randomGIAP));
                        break;
                    case 2213 :
                        int randomhp = Util.nextInt(1000,2500);
                         mvbt.options.add(new Item.ItemOption(22, randomhp));
                        break;
                    case 2214 :
                        int randomTC = Util.nextInt(200,400); 
                         mvbt.options.add(new Item.ItemOption(220, randomTC));
                        break;
                    case 2215 :
                        int randomki = Util.nextInt(1000,2500);
                         mvbt.options.add(new Item.ItemOption(23, randomki));
                        break;
                    case 2216 :
                        int randomCM = Util.nextInt(20,50);
                         mvbt.options.add(new Item.ItemOption(14, randomCM));
                        break;
                }
                
                mvbt.options.add(new Item.ItemOption(21, 400));// giap
                mvbt.options.add(new Item.ItemOption(36, 0)); // hoac 128 , 129
                mvbt.options.add(new Item.ItemOption(76, 0)); // hoac 140 ,141                
                mvbt.options.add(new Item.ItemOption(34, 0)); // hoac 140 ,141
                mvbt.options.add(new Item.ItemOption(randomSKH, 0)); // hoac 140 ,141
                mvbt.options.add(new Item.ItemOption(randomSKH >= 133 ? randomSKH + 3 : randomSKH + 12, 0)); // hoac 140 ,141

                Service.gI().dropItemMap(this.zone, mvbt);
            }
           
        } else {
            ItemMap mvbt = new ItemMap(this.zone, 2128, 3, this.location.x, this.location.y, plKill.id);
            Service.gI().dropItemMap(this.zone, mvbt);
        }
        return;
    }

    public long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }
            // System.out.println("dame:" + damage);
            damage = this.nPoint.subDameInjureWithDeff(damage/1000);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = damage / 1000;
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
    public void active() {
        super.active(); // To change body of generated methods, choose Tools | Templates.
        if (Util.canDoWithTime(st, 1200000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
        }
    }

    @Override
    public void joinMap() {
        super.joinMap(); // To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }

    private long st;

}
