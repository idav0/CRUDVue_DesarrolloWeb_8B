<template>
    <div>

        <b-card :title="`Articulo #${data.id}`"
            img-src="https://static.vecteezy.com/system/resources/previews/013/089/833/original/blank-white-book-png.png"
            img-alt="Image" img-top tag="article" style="max-width: 20rem; margin: 20px;" class="mb-2 shadow-card">
            <p class="card-text"><b>Nombre:</b> {{ data.nombre }}</p>
            <p class="card-text"><b>Autor:</b> {{ data.autor }}</p>
            <p class="card-text"><b>Género:</b> {{ data.genero }}</p>
            <p class="card-text"><b>Año de publicación:</b> {{ data.anio_publicacion }}</p>
            <!--Botones para actualizar y eliminar con respectovos iconos-->
            <div style="display: flex; justify-content: space-between;">
                <b-button @click="editBook" variant="primary">✏️ Editar</b-button>
                <b-button variant="danger" @click="deleteBook">🗑️ Eliminar</b-button>
            </div>
        </b-card>

    </div>
</template>
  
<script>
export default {
    props: {
        data: {
            type: Object,
            required: true
        }
    },
    methods: {
        editBook() {
            // Emitir el evento 'edit' con los datos del libro
            this.$emit('edit', this.data);
        },
        deleteBook() {
            this.$emit('delete', this.data.id)
        }
    }
}
</script>

<style scoped>
.shadow-card {
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
    transition: 0.3s;
}

.shadow-card:hover {
    box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 18px 60px 0 rgba(0, 0, 0, 0.19);
}
</style>