class CreateIdentities < ActiveRecord::Migration
  def change
    create_table :identities do |t|
      t.string :uid
      t.string :provider

      t.references :user

      t.timestamps null: false
      t.index :user_id
    end

  end
end
