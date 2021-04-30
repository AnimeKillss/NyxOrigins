package io.github.vanthanyx.originsplus.entity;

import io.github.vanthanyx.originsplus.registry.ModEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class LunarianPearlEntity extends ThrownItemEntity {
   public LunarianPearlEntity(EntityType<? extends LunarianPearlEntity> entityType, World world) {
      super(entityType, world);
   }

   public LunarianPearlEntity(World world, LivingEntity owner) {
      super(ModEntities.LUNARIAN_PEARL, owner, world);
   }

   @Environment(EnvType.CLIENT)
   public LunarianPearlEntity(World world, double x, double y, double z) {
      super(ModEntities.LUNARIAN_PEARL, x, y, z, world);
   }

   protected Item getDefaultItem() {
      return Items.ENDER_PEARL;
   }

   protected void onCollision(HitResult hitResult) {
      super.onCollision(hitResult);
      Entity entity = this.getOwner();

      for(int i = 0; i < 32; ++i) {
         this.world.addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0D, this.getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
      }

      if (!this.world.isClient && !this.removed) {
         if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
            if (serverPlayerEntity.networkHandler.getConnection().isOpen() && serverPlayerEntity.world == this.world && !serverPlayerEntity.isSleeping()) {

               if (entity.hasVehicle()) {
                  entity.stopRiding();
               }

               entity.requestTeleport(this.getX(), this.getY(), this.getZ());
               entity.fallDistance = 0.0F;
            }
         } else if (entity != null) {
            entity.requestTeleport(this.getX(), this.getY(), this.getZ());
            entity.fallDistance = 0.0F;
         }

         this.remove();
      }

   }

   public void tick() {
      Entity entity = this.getOwner();
      if (entity instanceof PlayerEntity && !entity.isAlive()) {
         this.remove();
      } else {
         super.tick();
      }

   }

   public Entity moveToWorld(ServerWorld destination) {
      Entity entity = this.getOwner();
      if (entity != null && entity.world.getRegistryKey() != destination.getRegistryKey()) {
         this.setOwner((Entity)null);
      }

      return super.moveToWorld(destination);
   }
}
