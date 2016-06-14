class AddOmniauthToUser < ActiveRecord::Migration
  def change
    #add_column :users, :provider, :string
    #add_column :users, :uid, :string
    add_column :users, :fullname, :string
    add_column :users, :image, :string

  end
end
